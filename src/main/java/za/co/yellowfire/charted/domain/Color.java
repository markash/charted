package za.co.yellowfire.charted.domain;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public enum Color {
    pale_faded_azure_blue("#558ed5"),
    medium_faded_yellow("#9ec350"),
    medium_faded_red("#953735"),
    vivid_red("#e48e50"),
    pale_light_azure_blue("#c6d9f1"),
    pale_very_light_yellow("#ebf1de"),
    pale_very_light_red("#f2dcdb"),
    pale_pastel_red("#fac090"),
    pale_vivid_red("#fdeada");

    private String hex;

    private Color(String hex) {
        this.hex = hex;
    }

    public String html() {
        return this.hex;
    }
}
