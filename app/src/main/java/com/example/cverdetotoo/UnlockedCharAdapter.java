package com.example.cverdetotoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * RecyclerView adapter for displaying unlocked characters
 * in a horizontal list on the profile screen.
 */
public class UnlockedCharAdapter extends RecyclerView.Adapter<UnlockedCharAdapter.ViewHolder> {

    public interface OnCharacterSelectedListener {
        void onCharacterSelected(CharacterModel character);
    }

    private List<CharacterModel> unlockedCharacters;
    private OnCharacterSelectedListener listener;

    public UnlockedCharAdapter(List<CharacterModel> unlockedCharacters, OnCharacterSelectedListener listener) {
        this.unlockedCharacters = unlockedCharacters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UnlockedCharAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unlocked_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnlockedCharAdapter.ViewHolder holder, int position) {
        CharacterModel character = unlockedCharacters.get(position);
        holder.bind(character, listener);
    }

    @Override
    public int getItemCount() {
        return unlockedCharacters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUnlockedChar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUnlockedChar = itemView.findViewById(R.id.imageUnlockedChar);
        }

        void bind(final CharacterModel character, OnCharacterSelectedListener listener) {
            imageUnlockedChar.setImageResource(character.getImageResId());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCharacterSelected(character);
                }
            });
        }
    }
}
