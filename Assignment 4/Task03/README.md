# Assignment 4 – Task 3: Campus Library Search Screen

**Name:** Juhi Surin  
**Register Number:** 192525148  
**Course:** CSA0919 Programming in Java

## Files
- `LibrarySearch.java` – complete AWT implementation.
- `Task3_Report.pdf` – report without personal details.
- `README.md` – setup, execution and screenshot instructions.

## Requirements implemented
- Title at the top.
- Search row with a `TextField`, AWT `Choice`, and Search button.
- Results area in the centre.
- Prev, Next and Reserve buttons at the bottom.
- Status message.
- Resizable window: the centre results list grows while the top and bottom controls retain preferred sizing.
- Search by all fields, title, author or category; pagination; reserve selection feedback.

## Java version
Use JDK 8 or later. Check your installed version with:
```bash
java -version
javac -version
```

## Run in VS Code / Windows
1. Install a JDK and the Java Extension Pack in VS Code.
2. Open the `Task03` folder.
3. Open the integrated terminal: **Terminal → New Terminal**.
4. Compile:
```bash
javac LibrarySearch.java
```
5. Run:
```bash
java LibrarySearch
```
The window titled **Campus Library Search** should open.

## Test the application
1. Leave the search field empty and click Search: all sample books should be listed.
2. Enter `java`, choose `Title`, click Search: matching title(s) should appear.
3. Choose `Author`, enter `silberschatz`, click Search: matching author records should appear.
4. Click Next and Prev to test pagination (the initial list contains more than one page).
5. Select a result and click Reserve: the status line should show the selected book's reservation request.
6. Click Reserve without selecting a result: an instruction should appear in the status line.

## Required resizing screenshots
The assignment specifically asks for screenshots at three window sizes. Capture genuine screenshots after running the program:
- `output_small.png` – resize the window to a smaller size.
- `output_medium.png` – use the initial/medium size.
- `output_large.png` – maximize or enlarge the window.

For each screenshot, verify that the centre results list expands/contracts and the search row/navigation controls remain grouped and usable. Save the screenshots in this `Task03` folder. Do not use a mock-up as execution evidence.

## Result
A working AWT library search screen is implemented with nested `BorderLayout` and `FlowLayout` panels. The centre results list receives the resizable space.

## GitHub submission
Place this folder inside `CSA09-192525148-Assignment4/Task03/`. Commit Task 3 separately and progressively, as required by the assignment.
