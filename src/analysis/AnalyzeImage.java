package analysis;

import java.net.URI;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logic.Tag;

@SuppressWarnings("deprecation")
public class AnalyzeImage{
	private HttpPost Request;
	private HttpClient HttpClient;
	private JSONObject Obj;

	public AnalyzeImage(){
		HttpClient = new DefaultHttpClient();
	    try {
	        URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");
	        builder.setParameter("visualFeatures", "Description,Tags");
	        builder.setParameter("language", "en");
	        URI uri = builder.build();
	        Request = new HttpPost(uri);
	        Request.setHeader("Content-Type", "application/json");
	        Request.setHeader("Ocp-Apim-Subscription-Key", "4037137ee03d43a58e406cec92afabf4");
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
		}  
	}
	
	public void analyze(String pUrl){
	    try {
	        StringEntity reqEntity = new StringEntity("{\"url\":\""+pUrl+"\"}");
	        Request.setEntity(reqEntity);
	        HttpResponse response = HttpClient.execute(Request);
	        HttpEntity entity = response.getEntity();
	        if (entity != null)
	        	Obj = new JSONObject(EntityUtils.toString(entity));
	    } catch (Exception e)  {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void tags(ArrayList<Tag> pTags){
		JSONArray array;
		try {
			array = Obj.getJSONArray("tags");
	    	for (int i = 0; i < array.length(); i++)
	    	{
	    		pTags.add(new Tag(array.getJSONObject(i).getString("name")));
	    		pTags.get(i).setConfidence(array.getJSONObject(i).getDouble("confidence"));
	    	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}