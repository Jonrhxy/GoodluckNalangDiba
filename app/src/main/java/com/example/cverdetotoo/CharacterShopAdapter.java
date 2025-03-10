package com.example.cverdetotoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CharacterShopAdapter extends RecyclerView.Adapter<CharacterShopAdapter.ViewHolder> {

    public interface OnCharacterActionListener {
        void onBuyClicked(CharacterModel character);
        void onSelectClicked(CharacterModel character);
    }

    private List<CharacterModel> characterList;
    private int userCoins;
    private OnCharacterActionListener listener;
    private String selectedCharacterId; // The currently equipped character's ID

    public CharacterShopAdapter(
            List<CharacterModel> characterList,
            int userCoins,
            String selectedCharacterId,
            OnCharacterActionListener listener
    ) {
        this.characterList = characterList;
        this.userCoins = userCoins;
        this.selectedCharacterId = selectedCharacterId;
        this.listener = listener;
    }

    public void setSelectedCharacterId(String selectedCharacterId) {
        this.selectedCharacterId = selectedCharacterId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the new card-based item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterShopAdapter.ViewHolder holder, int position) {
        CharacterModel character = characterList.get(position);
        holder.bind(character, listener, selectedCharacterId);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageCharacter;
        TextView textName, textCost;
        Button buttonAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            imageCharacter = itemView.findViewById(R.id.imageCharacter);
            textName = itemView.findViewById(R.id.textName);
            textCost = itemView.findViewById(R.id.textCost);
            buttonAction = itemView.findViewById(R.id.buttonAction);
        }

        void bind(final CharacterModel character,
                  OnCharacterActionListener listener,
                  String selectedCharacterId) {

            // Display the hero's image, name, and cost
            imageCharacter.setImageResource(character.getImageResId());
            textName.setText(character.getName());
            textCost.setText("Cost: " + character.getCost() + " coins");

            // If not unlocked, show "Buy" button
            if (!character.isUnlocked()) {
                buttonAction.setText("Buy");
                buttonAction.setEnabled(true);
                buttonAction.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onBuyClicked(character);
                    }
                });
            } else {
                // Character is unlocked, show "Equip" or "Equipped"
                if (character.getId().equals(selectedCharacterId)) {
                    buttonAction.setText("Equipped");
                    buttonAction.setEnabled(false);
                } else {
                    buttonAction.setText("Equip");
                    buttonAction.setEnabled(true);
                    buttonAction.setOnClickListener(v -> {
                        if (listener != null) {
                            listener.onSelectClicked(character);
                        }
                    });
                }
            }
        }
    }
}
