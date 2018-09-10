package com.peep.peep.home;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchAdapter {

    private static List<LocationClass> sColorSuggestions =
            new ArrayList<>(Arrays.asList(
                    new LocationClass("green"),
                    new LocationClass("Orchid")));

    public interface OnFindSuggestionsListener {
        void onResults(List<LocationClass> results);
    }



    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<LocationClass> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (LocationClass suggestion : sColorSuggestions) {
                        if (suggestion.getName().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
               /* Collections.sort(suggestionList, new Comparator<LocationClass>() {
                    @Override
                    public int compare(LocationClass lhs, LocationClass rhs) {
                        return lhs.getName() ? -1 : 0;
                    }
                });*/
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<LocationClass>) results.values);
                }
            }
        }.filter(query);

    }
}
