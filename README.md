# UAS Pemrograman Mobile 1 - Tekpro

Repository ini berisi project Ujian Akhir Semester (UAS) mata kuliah Pemrograman Mobile 
1. Aplikasi ini dirancang sebagai **Platform Knowledge Sharing Teknisi**, solusi bagi para teknisi untuk mendokumentasikan error di lapangan dan mencari solusi dari pengalaman rekan seprofesi.

## 👨‍🎓 Data Mahasiswa
* **Nama:** Adzan Takhyan Firdaus
* **NIM:** 312410043
* **Kelas:** TI.24.C1
* **Prodi:** Teknik Informatika
* **Dosen Pengampu:** Donny Maulana, S.Kom., M.M.S.I.

## 🔗 Link Project (Syarat UAS)
Berikut adalah kelengkapan dokumen manajemen project dan demo aplikasi:
* **🎥 Video Prototype (YouTube):** [  ]
* **📅 Timeline & Task (ClickUp):** [  ]

## ✨ Fitur & Alur Aplikasi

### 1. Smart Splash Screen (Location Based)
* **Fitur Deteksi Lokasi:** Sesuai syarat soal, aplikasi meminta izin akses GPS saat dibuka.
* **Logika:** Menampilkan Logo Pemda dan ucapan "Say Hello" yang dinamis menyesuaikan lokasi pengguna saat itu (misal: Jakarta vs Bekasi).

### 2. Authentication System
* **Halaman Login:** Gerbang akses keamanan untuk verifikasi user sebelum masuk ke menu utama.

### 3. Knowledge Management (Sharing Error)
* **Halaman Create Artikel:** Fitur bagi teknisi untuk menulis laporan error baru, berbagi tips, atau dokumentasi perbaikan yang dilakukan di lapangan.

### 4. Search Ecosystem (Pencarian Solusi)
Memudahkan teknisi mencari solusi spesifik dengan alur:
* **Halaman Pencarian:** Input kata kunci masalah/error.
* **Hasil Pencarian:** Menampilkan list artikel yang relevan.
* **Detail Pencarian:** Membuka halaman detail artikel untuk membaca solusi lengkap dari error tersebut.

## 🛠️ Spesifikasi Teknis (Backend Local)
Aplikasi ini menggunakan penyimpanan data lokal yang ringan dan cepat:
* **Database:** SQLite.
* **Struktur Data:** Menggunakan **2 Tabel** utama untuk menyimpan data User (Login) dan Data Artikel (Knowledge Base).

## 📂 Struktur Folder Repository
* `/tekpro` : **Source Code Aplikasi Android** (Project Android Studio).
* `/Mockup` : Desain High-Fidelity aplikasi.
* `/Storyboard` : Sketsa alur cerita aplikasi.
* `/User-Interface` : Aset desain antarmuka.
* `/User-Experience` : Dokumentasi alur pengalaman pengguna.
  
---
*Dibuat untuk memenuhi tugas UAS Semester Ganjil TA 2025/2026 - Universitas Pelita Bangsa*
