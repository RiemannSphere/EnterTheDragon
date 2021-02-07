package pl.messier.enterthedragon.rest.model;

public class SingleAverageResponse {
    private SingleAverageRequest request;
    private Double averageStockPrice;

    public SingleAverageResponse() {
    }

    public SingleAverageResponse(SingleAverageRequest request, Double averageStockPrice) {
        this.request = request;
        this.averageStockPrice = averageStockPrice;
    }

    public SingleAverageRequest getRequest() {
        return request;
    }

    public void setRequest(SingleAverageRequest request) {
        this.request = request;
    }

    public Double getAverageStockPrice() {
        return averageStockPrice;
    }

    public void setAverageStockPrice(Double averageStockPrice) {
        this.averageStockPrice = averageStockPrice;
    }
}
