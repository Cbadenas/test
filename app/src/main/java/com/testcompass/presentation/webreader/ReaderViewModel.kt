package com.testcompass.presentation.webreader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testcompass.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    @IoDispatcher private var dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _state = MutableStateFlow(ReaderUIState())
    var state = _state.asStateFlow()

    fun onAction(action: ReaderActions) {
        when (action) {
            is ReaderActions.ReadWebsite -> {
                readWebsite(action.url)
            }
        }

    }


    private fun readWebsite(url: String) = viewModelScope.launch(dispatcher) {

        _state.update { state ->
            state.copy(
                isLoading = true
            )
        }

        var result = ""
        val webContent = URL(url).readText()
        webContent.forEachIndexed { index, char ->
            if (index % 10 == 0) {
                result += char
            }
        }

        _state.update { state ->
            state.copy(
                website10s = getWebsite10s(webContent),
                uniques = findUniques(webContent).size,
                isLoading = false,
            )
        }
    }

    private fun getWebsite10s(content: String): ArrayList<String> {
        var arrayResult = arrayListOf<String>()
        content.forEachIndexed { index, char ->
            if (index % 10 == 0) {
                arrayResult.add(char.toString())
            }
        }

        return arrayResult
    }

    private fun findUniques(text: String): ArrayList<String> {

        var result = arrayListOf<String>()

        var p = Pattern.compile("[a-zA-Z]+")
        var m = p.matcher(text)

        var hashMap = HashMap<String, Int>()

        while (m.find()) {
            var word = m.group()

            if (!hashMap.containsKey(word)) {
                hashMap[word] = 1
            } else {
                hashMap[word] = hashMap[word]!! + 1
            }
        }

        var s = hashMap.keys
        var itr = s.iterator()

        while (itr.hasNext()) {
            var w = itr.next()

            if (hashMap[w]!! == 1) {
                result.add(w)
            }
        }

        return result

    }

}