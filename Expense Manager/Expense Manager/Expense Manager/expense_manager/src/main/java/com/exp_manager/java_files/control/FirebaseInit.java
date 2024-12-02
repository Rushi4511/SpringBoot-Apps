package com.exp_manager.java_files.control;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class FirebaseInit {
    private static Firestore db;
    public static Object amount;
    // static {
    //     try {
    //         initializeFirebase();
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }

    public static void initializeFirebase()  {

        try{
            // Load the JSON file as a resource
            InputStream serviceAccount = FirebaseInit.class.getResourceAsStream("/expensedemo.json");
            
            if (serviceAccount == null) {
                throw new FileNotFoundException("Resource not found: /expensedemo.json");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        // FileInputStream serviceAccount = new FileInputStream(
        //         "expense_manager\\src\\main\\resources\\expensedemo.json");
        // FirebaseOptions options = new FirebaseOptions.Builder()
        //         .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        //         .setDatabaseUrl("AIzaSyBHpQAqizA2rSs5BYchM0qnR8lqptn98ZM") // Replace with your database URL
        //         .build();

        // FirebaseApp.initializeApp(options);
        // db = FirestoreClient.getFirestore();
    //}

    public void addData(String collection, String document, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public DocumentSnapshot getData(String collection, String document)
            throws ExecutionException, InterruptedException {
        try {
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean authenticateUser(String username, String password) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()) {
            System.out.println("sdfghjk");
            String storedPassword = document.getString("Password");
            System.out.println(storedPassword);
            System.out.println(password);
            return password.equals(storedPassword);
        }
        System.out.println("sdfghjk");
        return false;
    }

    // Registration code
    public static boolean signUp(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("User created successfully: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            return false;
        }
    }

    // Login code logic
    public static boolean login(String email, String password) {
        try {

            String apiKey = "AIzaSyBHpQAqizA2rSs5BYchM0qnR8lqptn98ZM";
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("returnSecureToken", true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Login Successfully");
                return true;
            } else {
                System.out.println("Login Failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            return false;
        }
    }

    public void createRec(Map<String, Object> mapObj) throws InterruptedException, ExecutionException {
        Map<String, Object> teamData = new HashMap<>();
        // teamData.put("amount", amount);
        // teamData.put("time", "Rohit Sharma");
        // teamData.put("description", "Virat Kohli");
        // teamData.put("category", "Virat Kohli");
        // teamData.put("date", "Virat Kohli");
        System.out.println(db);
        System.out.println(teamData);
        ApiFuture<WriteResult> future = db.collection("Expense").document().set(mapObj);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }

    public static List<QueryDocumentSnapshot> fetchAllDocuments(String collectionName)
            throws InterruptedException, ExecutionException {
        // Asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();

        // Wait for the query to complete and get the results
        QuerySnapshot querySnapshot = query.get();

        // Get a list of all documents
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        return documents;
    }

    public static DocumentSnapshot readRec(String documentId) throws InterruptedException, ExecutionException {
        // Reference to the document
        DocumentReference docRef = db.collection("Expense").document(documentId);
        DocumentSnapshot docSnap = null;
        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            // Synchronously wait for the document to be available
            docSnap = future.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle potential exceptions
            e.printStackTrace();
        }
        return docSnap;
    }

    public void updateRec(Map<String, Object> mapObj1, String documentId)
            throws InterruptedException, ExecutionException {
        Map<String, Object> updateData = new HashMap<>();
        // updateData.put("amount", "J.Bumrah");
        System.out.println(updateData);
        ApiFuture<WriteResult> future = db.collection("Expense").document(documentId).set(mapObj1,
                SetOptions.merge());
        System.out.println("Update time : " + future.get().getUpdateTime());

    }

    public static Object fetchAttribute(String collectionName, String documentId, String attributeName)
            throws InterruptedException, ExecutionException {
        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = db.collection(collectionName).document(documentId).get();

        // Wait for the future to complete
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Get the specific attribute's data
            return document.get(attributeName);
        } else {
            System.out.println("No such document!");
            return null;
        }
    }

    public void deleteRec(String documentId) throws InterruptedException, ExecutionException {
        DocumentReference docRef = db.collection("Expense").document(documentId);

        ApiFuture<WriteResult> future = docRef.delete();
        System.out.println("Document deleted with update time: " + future.get().getUpdateTime());
    }

}
