package Database.places.models;

public class Place {
    private Integer placeId;
    private String placeName;
    private String placeAddress;
    private PlaceType placeType;

    public Place() {

    }

    public Place(Integer placeId, String placeName, String placeAddress, PlaceType placeType) {
        setPlaceId(placeId);
        if (placeName!=null&&!placeName.equals(""))
            setPlaceName(placeName);
        if (placeAddress!=null&&!placeAddress.equals(""))
            setPlaceAddress(placeAddress);
        setPlaceType(placeType);

    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        if (placeAddress!=null&&!placeAddress.equals(""))

            this.placeAddress = placeAddress;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {

        if (placeName!=null&&!placeName.equals(""))

            this.placeName = placeName;
    }

    @Override
    public String toString() {
        return placeName;
    }
}
