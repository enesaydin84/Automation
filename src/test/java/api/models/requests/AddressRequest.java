package api.models.requests;

public class AddressRequest {
    private final String street;
    private final String city;
    private final String zip;

    public AddressRequest(String street, String city, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getZip() { return zip; }
}

