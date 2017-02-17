package com.example.android.miwok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.entity.Word;

import java.util.List;

/**
 * Created by obed.gonzalez on 09/02/2017.
 */
public class AdapterListWords extends ArrayAdapter<Word> {

    private List<Word> listWords;
    private Context context;
    private int colorBackgroundId;

    public AdapterListWords(Context context, List<Word> words, int colorBackgroundId) {
        super(context, 0, words);
        this.listWords = words;
        this.context = context;
        this.colorBackgroundId = colorBackgroundId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View mConvertView = convertView;

        if (mConvertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_item_word, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtEnglish = (TextView) convertView.findViewById(R.id.ctm_list_word_txt_english);
            viewHolder.txtMiwok = (TextView) convertView.findViewById(R.id.ctm_list_word_txt_miwok);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.ctm_list_word_img);
            viewHolder.lytGral = (LinearLayout) convertView.findViewById(R.id.ctm_list_word_lyt_gnral);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) mConvertView.getTag();
        }

        Word word = listWords.get(position);
        if (word != null) {
            viewHolder.txtEnglish.setText(word.getEnglish());
            viewHolder.txtMiwok.setText(word.getMiwok());

            if (word.hasImage()) {
                viewHolder.imgIcon.setImageResource(word.getImageResourceId());
            } else {
                viewHolder.imgIcon.setVisibility(View.GONE);
            }

            viewHolder.lytGral.setBackground(context.getResources().getDrawable(colorBackgroundId));
        }

        return convertView;
    }


    static class ViewHolder {
        LinearLayout lytGral;
        TextView txtEnglish;
        TextView txtMiwok;
        ImageView imgIcon;
    }
}
