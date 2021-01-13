/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author orhan
 */
import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.*;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

import java.awt.Desktop;
import java.io.*;
import java.util.Locale;

public class Cloud {
      private static String ACCESS_TOKEN = "";
        
    public static void Cloud(JFrame frame,String path) throws IOException, DbxException {
       
 final String APP_KEY = "zd2mgehfrjz1qr8";
 final String APP_SECRET = "49fzz8s636oxezu";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
            "JavaToDoList", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
       // Have the user sign in and authorize the app.
       
     
       if(ACCESS_TOKEN == ""){
           
           String authorizeUrl = webAuth.start();

 JTextArea textarea= new JTextArea(authorizeUrl);
 textarea.setEditable(true);
 JOptionPane.showMessageDialog(frame, textarea);
 
  String code = JOptionPane.showInputDialog("Please input generated Auth Key ");
           
  System.out.println("1. Go to: " + authorizeUrl);
System.out.println("2. Click \"Allow\" (you might have to log in first)");
System.out.println("3. Copy the authorization code.");
//String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

DbxAuthFinish authFinish = webAuth.finish(code);


String accessToken = authFinish.getAccessToken();
 ACCESS_TOKEN = accessToken;
 
  DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
  String full_origin = "/"+path;
  try (InputStream in = new FileInputStream(path)) {
    FileMetadata metadata = client.files().uploadBuilder(full_origin)
        .uploadAndFinish(in);
}
       }else if(ACCESS_TOKEN != ""){

  


  
     

        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        String full = "/"+path;
        try (InputStream in = new FileInputStream(path)) {
    FileMetadata metadata = client.files().uploadBuilder(full)
        .uploadAndFinish(in);
}
        System.out.println(account.getName().getDisplayName());

       }


    }

   
    
    
}
