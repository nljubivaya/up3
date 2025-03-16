package com.example.up.domain

import android.net.http.HttpResponseCache.install
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Constant {
    val supabase = createSupabaseClient(supabaseUrl = "https://nhipkzulsthinmpfwznd.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5oaXBrenVsc3RoaW5tcGZ3em5kIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0OTgxNDAsImV4cCI6MjA1MDA3NDE0MH0.GaGBEgnn0-V-dr6N6UNUCA1YHbgNVVuCjSYDwygHF2A")
    {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}