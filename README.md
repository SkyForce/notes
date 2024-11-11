# 📝 Notes App

A simple notes management application with RESTful APIs for creating, retrieving, and deleting notes, as well as viewing word frequency statistics for each note.

## 🚀 Building and Running the Application

To build and run the application locally, use the following commands:

1. **Build and run the Application**
   ```bash
   mvn clean package
   docker-compose up --build

📖 API Endpoints
1. Create a New Note
   Endpoint: POST /note

Description: Creates a new note with a title, text, and optional tags.

Request:

```json
{
"title": "ABC",
"text": "word1 word2",
"tags": ["PERSONAL"]
}
```
Response: Returns the ID of the newly created note.

```json
"673201f1ea1f781d394ab307"
```

2. Update a New Note
   Endpoint: POST /note/{id}

Description: Updates a new note with a title, text, and optional tags.

Request:

```json
{
"title": "ABC",
"text": "word1 word2",
"tags": ["PERSONAL"]
}
```
Response: 200 OK or 404 if not found


2. Retrieve All Notes

   Endpoint: GET /notes

   Optional: tags param and paging: ?tags=tag1,tag2&page=0&size=20


   Description: Retrieves a paginated list of all notes with basic details.
   
   Response:
   
   ```json
   {
      "content": [
         {
            "id": "673201f1ea1f781d394ab307",
            "title": "ABC",
            "createdDate": "2024-11-11T13:09:05.036"
         },
         {
            "id": "793201f1ea1f781d394ab367",
            "title": "CBA",
            "createdDate": "2024-11-10T13:09:05.036"
         }
      ],
      "pageable": {
         "pageNumber": 0,
         "pageSize": 20,
         "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
          },
         "offset": 0,
         "paged": true,
         "unpaged": false
      },
      "totalPages": 1,
      "totalElements": 2,
      "last": true,
      "size": 20,
      "number": 0,
      "sort": {
         "empty": false,
         "sorted": true,
         "unsorted": false
      },
      "numberOfElements": 2,
      "first": true,
      "empty": false
   }
   ```

3. Retrieve a Note by ID

   Endpoint: GET /note/{id}

   Description: Fetches the details of a note by its unique ID.
   
   Example: GET /note/673201f1ea1f781d394ab307
   
   Response:
   
   ```json
   {
    "id": "673201f1ea1f781d394ab307",
    "text": "DOU dou asd dsa asd",
    "tags": ["PERSONAL"],
    "title": "ABC",
    "createdDate": "2024-11-11T18:33:55.751"
   }
   ```
   or 404 if not found


4. Delete a Note

   Endpoint: DELETE /note/{id}

   Description: Deletes a note by its ID.

   Example: DELETE /note/673201f1ea1f781d394ab307

   Response: 200 OK


5. Get Word Frequency Statistics for a Note

   Endpoint: GET /stats/{id}

   Description: Retrieves the frequency of each unique word in a note’s text.
   
   Example: GET /stats/673201f1ea1f781d394ab307
   
   Response:
   
   ```json
   {
    "word1": 3,
    "word2": 2,
    "word3": 1
   }
   ```
   or 404 if not found