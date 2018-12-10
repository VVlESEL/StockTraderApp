package de.bmtrading.stockfundamentals.keyfigures.ui.holder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import de.bmtrading.stockfundamentals.R;
import de.bmtrading.stockfundamentals.keyfigures.ui.model.CellModel;


public class PercentCellViewHolder extends AbstractViewHolder {
    public final TextView cell_textview;
    public final LinearLayout cell_container;

    public PercentCellViewHolder(View itemView) {
        super(itemView);
        cell_textview = itemView.findViewById(R.id.cell_data);
        cell_container = itemView.findViewById(R.id.cell_container);
    }

    public void setCellModel(CellModel p_jModel) {

        // Set text
        double amount = ((double)p_jModel.getData());
        cell_textview.setText(String.format("%.2f%%",amount));

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cell_textview.requestLayout();
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        super.setSelected(p_nSelectionState);

        if (p_nSelectionState == SelectionState.SELECTED) {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .selected_text_color));
        } else {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .unselected_text_color));
        }
    }
}
