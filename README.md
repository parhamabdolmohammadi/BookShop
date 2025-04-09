
# 📚 BestReads

A mobile app for discovering, saving, and organizing your reading journey — powered by the [Google Books API](https://developers.google.com/books).  
Built with **Kotlin**, **Jetpack Compose**, and **Room** — this app delivers a smooth, modern, and personalized reading experience.
Inspired by Goodreads, but with a focus on simplicity and ease of use.

---

## 🧠 Authors

- **[Danton Soares](https://github.com/Danton1)**
- **[Parham Abdolmohammadi](https://github.com/parhamabdolmohammadi)**

---

## ✨ Features

### 🔍 Book Search
- Search any keyword or genre using the Google Books API.
- Instantly displays a list of relevant books including:
    - Title
    - Author(s)
    - Publication Year
    - Cover Image (or fallback default)

### 🏠 Recommended Section
- Homepage includes a curated **"Recommended"** section based on general trends.
- Automatically localized — returns books in the **user's local language** and region thanks to the Google Books API.

### 📖 Book Details
- Click any book to open its full details page:
    - Enlarged cover image
    - Title, Author, Date, Description
    - Option to save the book to a personal list

### ✅ Add to List
- Easily save books to one of three lists:
    - **Read**
    - **Reading**
    - **Want To Read**
- Styled modal with one-click selection

### 🗃️ My Books Collection
- Navigate to your saved lists via:
    - "My Books" on the home screen
    - Submenus for each list
- Fully scrollable and persistently stored using **Room database**

### 🗑️ Remove Books
- Remove any saved book from your list with a long-press or trash icon
- Confirmation dialog prevents accidental deletion

### 💾 Local Persistence
- All saved books are stored locally using **Room DB**
- Your saved books remain even after closing or restarting the app

### 🚫 Duplicate Prevention
- Prevents adding the same book to the same list more than once

---

## 🌍 Language-Agnostic Design

This app is **inherently multilingual** due to the Google Books API:
- Search results and recommendations reflect the **user’s locale and language**
- If you search “Romance” in France, you’ll get French books
- Seamlessly adapts to user preferences without needing manual translations

> 🌐 Want to see what’s trending in another language? Just change your system language or location and search again!

---

## 🛠️ Tech Stack

| Tech                  | Role |
|-----------------------|------|
| **Kotlin**            | Main language |
| **Jetpack Compose**   | Declarative UI |
| **Room DB**           | Local data storage |
| **Google Books API**  | Book data and metadata |
| **Coil**              | Book cover image loading |
| **Material3**         | UI Components and themes |

---

## 🧪 Usage Scenarios

- Save books you’ve already read and rate them in the future
- Track your current reads and never lose your place
- Keep a "Want To Read" queue for planning future reads
- Browse suggested books in your language and region

---

## 🧼 Future Improvements

- ✅ Add rating system and personal notes
- 🌍 Manual language selection override
- 📈 User stats dashboard (most-read genres, total books read, etc.)
- ☁️ Cloud sync with Google login
- 🔄 Export/Import saved books

---

## 🚀 Get Started

To run locally:

1. Clone the repo
2. Open in Android Studio
3. Run on emulator or device (API 26+ recommended)
