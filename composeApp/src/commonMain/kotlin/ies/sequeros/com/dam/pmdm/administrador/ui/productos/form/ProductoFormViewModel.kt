package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoFormViewModel (private val item: ProductoDTO?,
                                onSuccess: (ProductoFormState) -> Unit): ViewModel() {

    private val _uiState = MutableStateFlow(ProductoFormState(
        name = item?.name ?: "",
        enabled = item?.enabled?:false,
        imagePath = item?.imagePath?:"",
        categoria = item?.categoriaId?:"",
        description = item?.description?:"",
        price = item?.price?:0.0

    ))
    val uiState: StateFlow<ProductoFormState> = _uiState.asStateFlow()

    // para saber si el formulario es valido
    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null)
        state.nombreError == null &&
                state.imagePathError ==null &&
                state.categoriaError == null &&

                !state.name.isBlank() &&
                state.imagePath.isNotBlank() &&
                state.categoria.isNotBlank()
        else{
            state.nombreError == null &&
                    state.imagePathError ==null &&
                    state.categoriaError == null &&

                    !state.name.isBlank() &&
                    state.imagePath.isNotBlank() &&
                    state.categoria.isNotBlank()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun onNombreChange(v: String) {
        _uiState.value = _uiState.value.copy(name = v, nombreError = validateNombre(v))
    }

    fun onImagePathChange(v: String) {
        _uiState.value = _uiState.value.copy(imagePath =  v, imagePathError =  validateImagePath(v))
    }

    fun onCategoriaChange(v: String) {
        _uiState.value = _uiState.value.copy(categoria = v, categoriaError = validateCategoria(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            enabled =  v
        )
    }

    fun onPriceChange(v: Double) {
        _uiState.value = _uiState.value.copy(
            price =  v
        )
    }

    fun onDescriptionChange(v: String) {
        _uiState.value = _uiState.value.copy(
            description =  v
        )
    }

    fun clear() {
        _uiState.value = ProductoFormState()
    }

    private fun validateNombre (nombre: String): String? {
        if (nombre.isBlank()) return "El nombre es obligatorio"
        if (nombre.length < 2) return "El nombre es muy corto"
        return null
    }

    private fun validateImagePath (path: String): String? {
        if (path.isBlank()) return "La imagen es obligatoria"
        return null
    }

    private fun validateCategoria (categoria: String): String? {
        if (categoria.isBlank()) return "La categoría es obligatoria"
        return null
    }

    private fun validatePrice (price: Double): String? {
        if (price <= 0) return "El precio debe ser mayor que 0"
        return null
    }

    private fun validateDescription (description: String): String? {
        if (description.isBlank()) return "La descripción es obligatoria"
        return null
    }

    fun onAdminChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(
            isadmin =   v

        )
    }

    private fun validateAll(): Boolean {
        val s = _uiState.value
        val nombreErr = validateNombre(s.name)
        val imageErr=validateImagePath(s.imagePath)
        val categoriaErr=validateCategoria(s.categoria)
        val newState = s.copy(
            nombreError = nombreErr,
            imagePathError = imageErr,
            categoriaError = categoriaErr,
            priceError = validatePrice(s.price) as Double?,
            descriptionError = validateDescription(s.description),

            submitted = true
        )
        _uiState.value = newState
        return listOf(nombreErr, imageErr, categoriaErr).all { it == null }
    }

    // se le pasan lambdas para ejecutar código en caso de éxito o error
    fun submit (
        onSuccess: ( ProductoFormState) -> Unit,
        onFailure: (( ProductoFormState) -> Unit)? = null
    ) {
        // se ejecuta en una corrutina, evitando que se bloque la interfaz gráfica
        viewModelScope.launch {
            val ok = validateAll()
            if (ok) {
                onSuccess(_uiState.value)
            } else {
                onFailure?.invoke(_uiState.value)
            }
        }
    }
}


























