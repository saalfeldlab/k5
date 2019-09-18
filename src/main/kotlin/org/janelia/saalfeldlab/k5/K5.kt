package org.janelia.saalfeldlab.k5

import com.google.gson.GsonBuilder
import org.janelia.saalfeldlab.n5.N5FSReader
import org.janelia.saalfeldlab.n5.N5FSWriter
import org.janelia.saalfeldlab.n5.hdf5.N5HDF5Reader
import org.janelia.saalfeldlab.n5.hdf5.N5HDF5Writer

class K5 {

    companion object {
        fun String.n5fsReader(builder: GsonBuilder? = null) = builder?.let { N5FSReader(this, it) } ?: N5FSReader(this)
        fun String.n5fsWriter(builder: GsonBuilder? = null) = builder?.let { N5FSWriter(this, it) } ?: N5FSWriter(this)

        fun String.hdf5Reader() = N5HDF5Reader(this)
        fun String.hdf5Writer() = N5HDF5Writer(this)
    }

}