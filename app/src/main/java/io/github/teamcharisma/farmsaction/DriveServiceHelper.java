/**
 * Copyright 2018 Google LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 */
public class DriveServiceHelper {
    private final Drive mDriveService;
    private final String fileName = "farmsaction.db";
    private String fileId = null;
    private Context context;

    DriveServiceHelper(Drive driveService, Context context) {
        mDriveService = driveService;
        this.context = context;
        try {
            fileId = retrieveFileId();
            if (fileId == null) {
                createDB();
            }
        } catch (UserRecoverableAuthIOException e) {
            ((AppCompatActivity)context).startActivityForResult(e.getIntent(), 132);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a text file in the user's My Drive folder and returns its file ID.
     */
    private void createDB() throws IOException {
        File metadata = new File()
                .setParents(Collections.singletonList("root"))
                .setName(fileName);

        File googleFile = mDriveService.files().create(metadata).execute();
        if (googleFile == null) {
            throw new IOException("Null result when requesting file creation.");
        }

        fileId = googleFile.getId();
    }

    private String retrieveFileId() throws IOException {
        String fileId = null;
        FileList files = mDriveService.files().list()
                .setSpaces("appDataFolder")
                .setFields("nextPageToken, files(id, name)")
                .setPageSize(10)
                .execute();
        for (File f : files.getFiles()) {
            if (f.getName().equals(fileName)) {
                fileId = f.getId();
            }
        }
        return fileId;
    }

    /**
     * Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and
     * contents.
     */
    public void readDB() throws IOException {

        if (fileId == null)
            return;


        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        mDriveService.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        outputStream.close();

        // Stream the file contents to a String.
        /*try (InputStream is = mDriveService.files().get(fileId).executeMediaAsInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        }*/
    }

    /**
     * Updates the file identified by {@code fileId} with the given {@code name} and {@code
     * content}.
     */
    public void saveDB(byte content[]) throws Exception {
        // Create a File containing any metadata changes.
        File metadata = new File().setName(fileName);
        if (mDriveService == null) return;
        // Convert content to an AbstractInputStreamContent instance.
        // ByteArrayContent contentStream = ;
        FileInputStream fileInputStream = context.openFileInput(fileName);
        InputStreamContent contentStream = new InputStreamContent(null, fileInputStream);
        // Update the metadata and contents.
        mDriveService.files().update(fileId, metadata, contentStream).execute();
    }
}