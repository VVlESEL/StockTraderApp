package de.bmtrading.stockfundamentals.keyfigures.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import java.util.List;

import de.bmtrading.stockfundamentals.R;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.CellViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.ColumnHeaderViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.MoneyCellViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.PercentCellViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.PercentChangeCellViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.holder.RowHeaderViewHolder;
import de.bmtrading.stockfundamentals.keyfigures.ui.model.CellModel;
import de.bmtrading.stockfundamentals.keyfigures.ui.model.ColumnHeaderModel;
import de.bmtrading.stockfundamentals.keyfigures.ui.model.RowHeaderModel;
import iex.Stock;

public class MyTableAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel,
        CellModel> {

    private MyTableViewModel myTableViewModel;

    public MyTableAdapter(Context p_jContext) {
        super(p_jContext);

        this.myTableViewModel = new MyTableViewModel();
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;

        switch (viewType) {
            case MyTableViewModel.MONEY_TYPE:
                // Get money cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout
                        .keyfigures_money_cell_layout, parent, false);

                // Create the relevant view holder
                return new MoneyCellViewHolder(layout);
            case MyTableViewModel.PERCENT_CHANGE_TYPE:
                // Get default cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout
                        .keyfigures_cell_layout, parent, false);

                // Create the relevant view holder
                return new PercentChangeCellViewHolder(layout);
            case MyTableViewModel.PERCENT_TYPE:
                // Get default cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout
                        .keyfigures_cell_layout, parent, false);

                // Create the relevant view holder
                return new PercentCellViewHolder(layout);
            default:
                // Get default Cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout.keyfigures_cell_layout,
                        parent, false);

                // Create a Cell ViewHolder
                return new CellViewHolder(layout);
        }
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object p_jValue, int p_nXPosition, int p_nYPosition) {
        CellModel cell = (CellModel) p_jValue;

        if (holder instanceof CellViewHolder) {
            // Get the holder to update cell item text
            ((CellViewHolder) holder).setCellModel(cell, p_nXPosition);
        } else if (holder instanceof PercentChangeCellViewHolder) {
            ((PercentChangeCellViewHolder) holder).setCellModel(cell);
        } else if (holder instanceof PercentCellViewHolder) {
            ((PercentCellViewHolder) holder).setCellModel(cell);
        } else if (holder instanceof MoneyCellViewHolder) {
            ((MoneyCellViewHolder) holder).setCellModel(cell);
        }

    }

    @Override
    public AbstractSorterViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .keyfigures_column_header_layout, parent, false);

        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nXPosition) {
        ColumnHeaderModel columnHeader = (ColumnHeaderModel) p_jValue;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;

        columnHeaderViewHolder.setColumnHeaderModel(columnHeader, p_nXPosition);
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.keyfigures_row_header_layout,
                parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nYPosition) {

        RowHeaderModel rowHeaderModel = (RowHeaderModel) p_jValue;

        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(rowHeaderModel.getData());

    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.keyfigures_corner_layout, null, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return myTableViewModel.getCellItemViewType(position);
    }


    /**
     * This method is not a generic Adapter method. It helps to generate lists from single user
     * list for this adapter.
     */
    public void setStockList(List<Stock> stockList) {
        // Generate the lists that are used to TableViewAdapter
        myTableViewModel.generateListForTableView(stockList);

        // Now we got what we need to show on TableView.
        setAllItems(myTableViewModel.getColumHeaderModeList(), myTableViewModel
                .getRowHeaderModelList(), myTableViewModel.getCellModelList());
    }
}
