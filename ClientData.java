package paquete_principal;

public class ClientData {
    private String uniqueId;
    private String givenNames;
    private String familyNames;
    private String phoneNumber;
    private String emailAddress;
    private String residentialAddress;
    private String postalCode;

    public ClientData(String uniqueId, String givenNames, String familyNames, String phoneNumber, String emailAddress, String residentialAddress, String postalCode) {
        this.uniqueId = uniqueId;
        this.givenNames = givenNames;
        this.familyNames = familyNames;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.residentialAddress = residentialAddress;
        this.postalCode = postalCode;
    }

    // Métodos de acceso (getters)
    public String getUniqueId() { return uniqueId; }
    public String getGivenNames() { return givenNames; }
    public String getFamilyNames() { return familyNames; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getResidentialAddress() { return residentialAddress; }
    public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return "ID: " + uniqueId + ", Nombres Completos: " + givenNames + " " + familyNames + ", Teléfono: " + phoneNumber;
    }
}