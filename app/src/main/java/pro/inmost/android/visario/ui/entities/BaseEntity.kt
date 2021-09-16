package pro.inmost.android.visario.ui.entities


/**
 * Every entity must implemented this interface
 * to use in [pro.inmost.android.visario.ui.adapters.GenericListAdapterWithDiffUtil]
 * for comparing items by id
 *
 * @constructor Create empty Base entity
 */
interface BaseEntity{
    val baseId: String
}
