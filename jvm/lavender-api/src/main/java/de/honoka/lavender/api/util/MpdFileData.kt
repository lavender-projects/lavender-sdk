package de.honoka.lavender.api.util

import de.honoka.sdk.util.kotlin.text.addElementByStr
import de.honoka.sdk.util.text.XmlUtils
import org.dom4j.Element

@Suppress("MemberVisibilityCanBePrivate")
class MpdFileData {

    data class Basic(

        var duration: Double? = null,

        var minBufferTime: Double? = null
    )

    data class Media(

        var streamUrl: String? = null,

        var bandwidth: Long? = null,

        var mimeType: String? = null,

        var codecs: String? = null,

        var width: Int? = null,

        var height: Int? = null,

        var frameRate: String? = null,

        var sar: String? = null,

        var startWithSap: Int? = null,

        var segmentBase: SegmentBase = SegmentBase()
    )

    data class SegmentBase(

        var initialization: String? = null,

        var indexRange: String? = null
    )

    var basic = Basic()

    var video = Media()

    var audio = Media()

    @Suppress("XmlPathReference", "XmlUnusedNamespaceDeclaration")
    private fun root(): Element {
        val xmlStr = """
            <MPD xmlns="urn:mpeg:dash:schema:mpd:2011"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="urn:mpeg:DASH:schema:MPD:2011 DASH-MPD.xsd"
                 profiles="urn:mpeg:dash:profile:isoff-on-demand:2011"
                 type="static" 
                 mediaPresentationDuration="PT${basic.duration}S"
                 minBufferTime="PT${basic.minBufferTime}S" />
        """.trimIndent()
        return XmlUtils.readRootElement(xmlStr)
    }

    private fun video(): Element {
        val xmlStr = """
            <AdaptationSet codecs="${video.codecs}" 
                           mimeType="${video.mimeType}" 
                           segmentAlignment="true" 
                           startWithSAP="${video.startWithSap}">
                <Representation id="1"
                                width="${video.width}"
                                height="${video.height}" 
                                sar="${video.sar}"
                                frameRate="${video.frameRate}"
                                bandwidth="${video.bandwidth}">
                    <BaseURL>${video.streamUrl}</BaseURL>
                    <SegmentBase indexRange="${video.segmentBase.indexRange}">
                        <Initialization range="${video.segmentBase.initialization}" />
                    </SegmentBase>
                </Representation>
            </AdaptationSet>
        """.trimIndent()
        return XmlUtils.parseElement(xmlStr)
    }

    private fun audio(): Element {
        val xmlStr = """
            <AdaptationSet codecs="${audio.codecs}" 
                           mimeType="${audio.mimeType}" 
                           segmentAlignment="true" 
                           startWithSAP="${audio.startWithSap}">
                <AudioChannelConfiguration schemeIdUri="urn:mpeg:dash:23003:3:audio_channel_configuration:2011" 
                                           value="2" />
                <Representation id="2" bandwidth="${audio.bandwidth}">
                    <BaseURL>${audio.streamUrl}</BaseURL>
                    <SegmentBase indexRange="${audio.segmentBase.indexRange}">
                        <Initialization range="${audio.segmentBase.initialization}" />
                    </SegmentBase>
                </Representation>
            </AdaptationSet>
        """.trimIndent()
        return XmlUtils.parseElement(xmlStr)
    }

    override fun toString(): String = root().apply {
        addElementByStr("<Period />").run {
            add(video())
            add(audio())
        }
    }.document.asXML()
}
