package com.extremeSolution.ecommerce.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.extremeSolution.ecommerce.app.utils.Constants.PREFS
import com.extremeSolution.ecommerce.app.utils.Constants.PREF_CATEGORIES
import com.extremeSolution.ecommerce.data.datastore.AppDataStore.PreferenceKeys.categories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS)
    }

    private val preferences = context.dataStore.data
    private val gson = Gson()

    private object PreferenceKeys {
        val categories = stringPreferencesKey(PREF_CATEGORIES)
    }

    suspend fun saveCategories(categoriesList: List<String>) {
        val categoriesJson = gson.toJson(categoriesList)
        context.dataStore.edit { preferences ->
            preferences[categories] = categoriesJson
        }
    }

    val readCategories: Flow<List<String>> =
        preferences.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedCoursePrice =
                preferences[categories] ?: ""
            gson.fromJson(selectedCoursePrice, object : TypeToken<List<String>>() {}.type)
        }
}