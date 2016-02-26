package rDataBase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;
import org.textmining.text.extraction.WordExtractor;

import android.content.Context;
import android.os.Environment;

public class FileService {
	@SuppressWarnings("unused")
	private Context context;

	public FileService(Context context) {
		this.context=context;
		}
	public FileService(){
		
	}
	public String getFileString(InputStream inputStream){
		
		//ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		String res="";
		
		 
		try{ 
			int length = inputStream.available();         
		    byte [] buffer = new byte[length];        
           
		   inputStream.read(buffer);            
		   inputStream.close();
		   res = EncodingUtils.getString(buffer, "gbk");   
		   
		   
		  }catch(Exception e){ 

		      e.printStackTrace();         

		   } 
		
		return res;
	}
	public String readword(String filename) throws FileNotFoundException{
		File file=new File(filename);
		String text="";
		try {
			FileInputStream inputStream=new FileInputStream(file);
			WordExtractor extractor = null;
			extractor = new WordExtractor(); 
			  text = extractor.extractText(inputStream);
				inputStream.close();
		}catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }

				
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return text;
	}
	
	public String getFileFromSdcard(String filename){
		FileInputStream inputStream=null;
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		File file=new File(Environment.getExternalStorageDirectory(),filename);
		if((Environment.MEDIA_MOUNTED).equals(Environment.getExternalStorageState())){
			try{
				inputStream=new FileInputStream(file);
				int len;
				byte[] data=new byte[1024];
				while((len=inputStream.read(data))!=-1){
					outputStream.write(data,0,len);}
				}catch(FileNotFoundException e){
					e.printStackTrace();
					
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(inputStream!=null){
						try{
							inputStream.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
			}
		}
		return new String(outputStream.toByteArray());
		
	}
	public boolean saveContentToSdcard(String filename, String content){
		boolean flag=false;
		FileOutputStream fileOutputStream=null;
		File file=new File(Environment.getExternalStorageDirectory(),filename);
		if((Environment.MEDIA_MOUNTED).equals(Environment.getExternalStorageState())){
			try{
				fileOutputStream=new FileOutputStream(file);
				fileOutputStream.write(content.getBytes());
				}catch(FileNotFoundException e){
					e.printStackTrace();
					
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(fileOutputStream!=null){
						try{
							fileOutputStream.close();
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
			
		}
		return flag;
	}

}
