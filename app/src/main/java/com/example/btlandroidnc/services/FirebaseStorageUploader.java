package com.example.btlandroidnc.services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.btlandroidnc.common.HandleFileUploadListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageUploader {
    private final Context context;

    private final FirebaseStorage storage;
    private final StorageReference storage_reference;

    public FirebaseStorageUploader(Context context) {
        this.context = context;

        this.storage = FirebaseStorage.getInstance();

        this.storage_reference = this.storage.getReference();
    }

    public void upload_file(@NonNull Uri file, HandleFileUploadListener listener) {
        StorageReference file_ref = storage_reference.child("/" + file.getLastPathSegment());

        UploadTask uploadTask = file_ref.putFile(file);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            file_ref.getDownloadUrl().addOnSuccessListener(uri -> {
                String download_url = uri.toString();

                if (listener != null) {
                    listener.on_upload_success(download_url);
                }
            });
        }).addOnFailureListener(e -> {
            Log.e("FirebaseStorage", "Error uploading file", e);

            if (listener != null) {
                listener.on_upload_failure(e.getMessage());
            }
        });
    }
}
