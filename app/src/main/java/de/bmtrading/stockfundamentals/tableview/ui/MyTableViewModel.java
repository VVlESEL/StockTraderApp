package de.bmtrading.stockfundamentals.tableview.ui;

import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import de.bmtrading.stockfundamentals.tableview.ui.model.CellModel;
import de.bmtrading.stockfundamentals.tableview.ui.model.ColumnHeaderModel;
import de.bmtrading.stockfundamentals.tableview.ui.model.RowHeaderModel;
import iex.Stock;

public class MyTableViewModel {
    public static final int GENDER_TYPE = 1;
    public static final int MONEY_TYPE = 2;

    private List<ColumnHeaderModel> mColumnHeaderModelList;
    private List<RowHeaderModel> mRowHeaderModelList;
    private List<List<CellModel>> mCellModelList;

    public int getCellItemViewType(int column) {
        switch (column) {
            case 5:
                // 5. column header is gender.
                return GENDER_TYPE;
            case 8:
                // 8. column header is Salary.
                return MONEY_TYPE;
            default:
                return 0;
        }
    }
/*
    public int getColumnTextAlign(int column) {
        switch (column) {
            // Id
            case 0:
                return Gravity.CENTER;
            // Name
            case 1:
                return Gravity.LEFT;
            // Nickname
            case 2:
                return Gravity.LEFT;
            // Email
            case 3:
                return Gravity.LEFT;
            // BirthDay
            case 4:
                return Gravity.CENTER;
            // Gender (Sex)
            case 5:
                return Gravity.CENTER;
            // Age
            case 6:
                return Gravity.CENTER;
            // Job
            case 7:
                return Gravity.LEFT;
            // Salary
            case 8:
                return Gravity.CENTER;
            // CreatedAt
            case 9:
                return Gravity.CENTER;
            // UpdatedAt
            case 10:
                return Gravity.CENTER;
            // Address
            case 11:
                return Gravity.LEFT;
            // Zip Code
            case 12:
                return Gravity.RIGHT;
            // Phone
            case 13:
                return Gravity.RIGHT;
            // Fax
            case 14:
                return Gravity.RIGHT;
            default:
                return Gravity.CENTER;
        }

    }
*/
    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Company"));
        list.add(new ColumnHeaderModel("Market Cap"));
        list.add(new ColumnHeaderModel("Beta"));
        list.add(new ColumnHeaderModel("52W High"));
        list.add(new ColumnHeaderModel("52W Low"));
        list.add(new ColumnHeaderModel("52W Change"));
        list.add(new ColumnHeaderModel("Short Interest"));
        list.add(new ColumnHeaderModel("Dividend Rate"));
        list.add(new ColumnHeaderModel("Dividend Yield"));
        list.add(new ColumnHeaderModel("Latest EPS"));
        //list.add(new ColumnHeaderModel("Latest EPS Date"));
        list.add(new ColumnHeaderModel("Float"));
        list.add(new ColumnHeaderModel("Return on Equity"));
        list.add(new ColumnHeaderModel("Return on Assets"));
        list.add(new ColumnHeaderModel("Consensus EPS"));
        list.add(new ColumnHeaderModel("EPS Surprise Percent"));
        list.add(new ColumnHeaderModel("EBITDA"));
        list.add(new ColumnHeaderModel("Revenue"));
        list.add(new ColumnHeaderModel("Gross Profit"));
        list.add(new ColumnHeaderModel("Cash"));
        list.add(new ColumnHeaderModel("Dept"));
        list.add(new ColumnHeaderModel("12M Trailing EPS"));
        list.add(new ColumnHeaderModel("Revenue per Share"));
        list.add(new ColumnHeaderModel("Revenue per Employee"));
        list.add(new ColumnHeaderModel("PE Ratio"));
        list.add(new ColumnHeaderModel("PE Ratio High"));
        list.add(new ColumnHeaderModel("PE Ratio Low"));
        list.add(new ColumnHeaderModel("Profit Margin"));
        list.add(new ColumnHeaderModel("Price to Sales"));
        list.add(new ColumnHeaderModel("Price to Book"));
        list.add(new ColumnHeaderModel("200 Day Moving Avg"));
        list.add(new ColumnHeaderModel("50 Day Moving Avg"));
        list.add(new ColumnHeaderModel("Institutional Percent"));
        list.add(new ColumnHeaderModel("Insider Percent"));
        list.add(new ColumnHeaderModel("Short Ratio"));
        list.add(new ColumnHeaderModel("5Y Change"));
        list.add(new ColumnHeaderModel("2Y Change"));
        list.add(new ColumnHeaderModel("1Y Change"));
        list.add(new ColumnHeaderModel("YTD Change"));
        list.add(new ColumnHeaderModel("6M Change"));
        list.add(new ColumnHeaderModel("3M Change"));
        list.add(new ColumnHeaderModel("1M Change"));
        list.add(new ColumnHeaderModel("30D Change"));
        list.add(new ColumnHeaderModel("5D Change"));

        return list;
    }

    private List<List<CellModel>> createCellModelList(List<Stock> stockList) {
        List<List<CellModel>> lists = new ArrayList<>();

        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            int row = 1;
            list.add(new CellModel(row++ + "-" + i, stock.getCompanyName()));               //Company Name
            list.add(new CellModel(row++ + "-" + i, stock.getMarketcap()));                 //Market Cap
            list.add(new CellModel(row++ + "-" + i, stock.getBeta()));                      //Beta
            list.add(new CellModel(row++ + "-" + i, stock.getWeek52high()));                //52W High
            list.add(new CellModel(row++ + "-" + i, stock.getWeek52low()));                 //52W Low
            list.add(new CellModel(row++ + "-" + i, stock.getWeek52change()));              //52W Change
            list.add(new CellModel(row++ + "-" + i, stock.getShortInterest()));             //Short Interest
            list.add(new CellModel(row++ + "-" + i, stock.getDividendRate()));              //Dividend Rate
            list.add(new CellModel(row++ + "-" + i, stock.getDividendYield()));             //Dividend Yield
            list.add(new CellModel(row++ + "-" + i, stock.getLatestEPS()));                 //Latest EPS
            //list.add(new CellModel(row++ + "-" + i, stock.getLatestEPSDate()));             //Latest EPS Date
            list.add(new CellModel(row++ + "-" + i, stock.getFloat()));                     //Float
            list.add(new CellModel(row++ + "-" + i, stock.getReturnOnEquity()));            //Return on Equity
            list.add(new CellModel(row++ + "-" + i, stock.getReturnOnAssets()));            //Return on Assets
            list.add(new CellModel(row++ + "-" + i, stock.getConsensusEPS()));              //Consensus EPS
            list.add(new CellModel(row++ + "-" + i, stock.getEPSSurprisePercent()));        //EPS Surprise Percent
            list.add(new CellModel(row++ + "-" + i, stock.getEBITDA()));                    //EBITDA
            list.add(new CellModel(row++ + "-" + i, stock.getRevenue()));                   //Revenue
            list.add(new CellModel(row++ + "-" + i, stock.getGrossProfit()));               //Gross Profit
            list.add(new CellModel(row++ + "-" + i, stock.getCash()));                      //Cash
            list.add(new CellModel(row++ + "-" + i, stock.getDebt()));                      //Dept
            list.add(new CellModel(row++ + "-" + i, stock.getTtmEPS()));                    //12M Trailing EPS
            list.add(new CellModel(row++ + "-" + i, stock.getRevenuePerShare()));           //Revenue per Share
            list.add(new CellModel(row++ + "-" + i, stock.getRevenuePerEmployee()));        //Revenue per Employee
            list.add(new CellModel(row++ + "-" + i, stock.getPeRatio()));                   //PE Ratio
            list.add(new CellModel(row++ + "-" + i, stock.getPeRatioHigh()));               //PE Ratio High
            list.add(new CellModel(row++ + "-" + i, stock.getPeRatioLow()));                //PE Ratio Low
            list.add(new CellModel(row++ + "-" + i, stock.getProfitMargin()));              //Profit Margin
            list.add(new CellModel(row++ + "-" + i, stock.getPriceToSales()));              //Price to Sales
            list.add(new CellModel(row++ + "-" + i, stock.getPriceToBook()));               //Price to Book
            list.add(new CellModel(row++ + "-" + i, stock.getDay200MovingAvg()));           //200 Day Moving Avg
            list.add(new CellModel(row++ + "-" + i, stock.getDay50MovingAvg()));            //50 Day Moving Avg
            list.add(new CellModel(row++ + "-" + i, stock.getInstitutionPercent()));        //Institutional Percent
            list.add(new CellModel(row++ + "-" + i, stock.getInsiderPercent()));            //Insider Percent
            list.add(new CellModel(row++ + "-" + i, stock.getShortRatio()));                //Short Ratio
            list.add(new CellModel(row++ + "-" + i, stock.getYear5ChangePercent()));        //5Y Change
            list.add(new CellModel(row++ + "-" + i, stock.getYear2ChangePercent()));        //2Y Change
            list.add(new CellModel(row++ + "-" + i, stock.getYear1ChangePercent()));        //1Y Change
            list.add(new CellModel(row++ + "-" + i, stock.getYtdChangePercent()));          //YTD Change
            list.add(new CellModel(row++ + "-" + i, stock.getMonth6ChangePercent()));       //6M Change
            list.add(new CellModel(row++ + "-" + i, stock.getMonth3ChangePercent()));       //3M Change
            list.add(new CellModel(row++ + "-" + i, stock.getMonth1ChangePercent()));       //1M Change
            list.add(new CellModel(row++ + "-" + i, stock.getDay30ChangePercent()));        //30D Change
            list.add(new CellModel(row++ + "-" + i, stock.getDay5ChangePercent()));         //5D Change

            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList(List<Stock> stockList) {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);
            list.add(new RowHeaderModel(stock.getSymbol()));
        }
        return list;
    }

    public List<ColumnHeaderModel> getColumHeaderModeList() {
        return mColumnHeaderModelList;
    }

    public List<RowHeaderModel> getRowHeaderModelList() {
        return mRowHeaderModelList;
    }

    public List<List<CellModel>> getCellModelList() {
        return mCellModelList;
    }

    public void generateListForTableView(List<Stock> stocks) {
        mColumnHeaderModelList = createColumnHeaderModelList();
        mCellModelList = createCellModelList(stocks);
        mRowHeaderModelList = createRowHeaderList(stocks);
    }
}