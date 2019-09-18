package org.janelia.saalfeldlab.k5

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.invoke.MethodHandles

internal class K5Test {

    private lateinit var directory: File

    @Before
    fun createTestDirectory() {
        directory = createTempDir("k5-test")
        LOG.debug("Created temp test dir {}", directory)
    }

    @After
    fun deleteTestDirectory() {
        LOG.debug("Deleting temp test dir {}", directory)
        directory.deleteRecursively()
    }

    @Test
    fun testFileSystem() {
        val container = directory.resolve(N5_CONTAINER)
        Assert.assertFalse(container.exists())
        with (K5) {
            container.absolutePath.n5fsWriter().let {
                it.createGroup(TEST_GROUP)
                it.setAttribute(TEST_GROUP, TEST_ATTRIBUTE, TEST_VALUE)
                Assert.assertTrue(container.exists())
                Assert.assertTrue(container.isDirectory)
            }
            container.absolutePath.n5fsReader().let {
                Assert.assertTrue(it.exists(TEST_GROUP))
                Assert.assertTrue(TEST_ATTRIBUTE in it.listAttributes(TEST_GROUP))
                Assert.assertEquals(TEST_VALUE, it.getAttribute(TEST_GROUP, TEST_ATTRIBUTE, TEST_VALUE::class.java))
            }
        }
    }

    @Test
    fun testHDF5() {
        val container = directory.resolve(HDF_CONTAINER)
        Assert.assertFalse(container.exists())
        with (K5) {
            container.absolutePath.hdf5Writer().use {
                it.createGroup(TEST_GROUP)
                it.setAttribute(TEST_GROUP, TEST_ATTRIBUTE, TEST_VALUE)
                Assert.assertTrue(container.exists())
                Assert.assertTrue(container.isFile)
            }
            container.absolutePath.hdf5Reader().use {
                Assert.assertTrue(it.exists(TEST_GROUP))
                Assert.assertTrue(TEST_ATTRIBUTE in it.listAttributes(TEST_GROUP))
                Assert.assertEquals(TEST_VALUE, it.getAttribute(TEST_GROUP, TEST_ATTRIBUTE, TEST_VALUE::class.java))
            }
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

        private val N5_CONTAINER = "n5.n5"

        private val HDF_CONTAINER = "hdf.h5"

        private val TEST_GROUP = "TEST_GROUP"

        private val TEST_ATTRIBUTE = "TEST_ATTRIBUTE"

        private val TEST_VALUE = "TEST_VALUE"
    }

}