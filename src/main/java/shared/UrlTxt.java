 
package shared;
 
public record UrlTxt(String url,String txt) {
    @Override
    public String toString() {
        return  url + " " + txt.length();
    }
}
