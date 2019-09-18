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

        val String.n5fsReader: N5FSReader
            get() = this.n5fsReader()
        val String.n5fsWriter: N5FSWriter
            get() = this.n5fsWriter()

        fun String.hdf5Reader() = N5HDF5Reader(this)
        fun String.hdf5Writer() = N5HDF5Writer(this)

        val String.hdf5Reader: N5HDF5Reader
            get() = this.hdf5Reader()
        val String.hdf5Writer: N5HDF5Writer
            get() = this.hdf5Writer()
    }

}