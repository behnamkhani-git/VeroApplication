package khani.behnam.displaytasks.model.mapper

interface UiMapper<E, V> {
    fun mapToView(input:E): V
}