package pikkup.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonSetter;

public class PriceEstimateCollectionModel 
        extends java.util.Observable {

    private List<PriceEsitmateModel> prices;
    /** GETTER
     * List of price estimates
     */
    @JsonGetter("prices")
    public List<PriceEsitmateModel> getPrices ( ) { 
        return this.prices;
    }
    
    /** SETTER
     * List of price estimates
     */
    @JsonSetter("prices")
    public void setPrices (List<PriceEsitmateModel> value) { 
        this.prices = value;
        notifyObservers(this.prices);
    }
 
}