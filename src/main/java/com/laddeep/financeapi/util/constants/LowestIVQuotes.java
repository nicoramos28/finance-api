package com.laddeep.financeapi.util.constants;

import com.laddeep.financeapi.component.StockBean;
import com.laddeep.financeapi.entity.db.Quote;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LowestIVQuotes {

    private StockBean dbManager;

    private String[] watchlist = {
        "SPY","AAPL","QQQ","NIO","BA","PLTR","FB","HYG","AMD","VXX","BAC","UVXY","AMZN","BABA","AAL","MSFT","XLE","M","GE","SQ","T"
        ,"XLF","WFC","JD","NVDA","SQQQ","EWZ","PBR","TQQQ","CCL","XLI","NKE","SLV","DKNG","NOK","JPM","MO","DIS","XOM","MARA","C","WMT","WKHS"
        ,"PLUG","TGT","UAL","SNOW","EFA","CCIV","CSCO","CCIV","EFA","UBER","VZ","TSM","V","PYPL","PDD","BLNK","DIA","SBUX","CAT"
        ,"QCOM","PFE","GDX","HD","GM","CRM","CVS","XOP","ORCL","KR","SHOP","NCLH","XPEV","BP","QS","MRO","SKLZ","NKLA","FUBO","PINS","FCEL"
        ,"GS","OXY","CMCSA","FSR","MX","PTON","SPCE","CVX","KO","LI","JETS","KMI","ZM","COST","XLU","XLB","ABNB","CCIV","UPS","MT","RH"
        ,"MRVL","PENN","IPOE","GOOGL","HAL","JMIA","GOLD","PSTH","LOW","BTWN","FDX","ADBE"
    };

    public LowestIVQuotes(StockBean dbManager){
        this.dbManager = dbManager;
    }

    public List<Quote> getLowestIVQuotes(){
        List<Quote> quoteList = new ArrayList<>();
        for(int i = 0; i< watchlist.length; i++){
            quoteList.add(dbManager.get(watchlist[i]));
        }
        return quoteList;
    }
}
