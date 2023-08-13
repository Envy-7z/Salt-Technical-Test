package com.wisnu.salttechnicaltest.models

import com.google.gson.annotations.SerializedName
import com.wisnu.salttechnicaltest.utils.BaseResponse

data class NewsSourcesResponse(
    @SerializedName("sources") var sources: List<SourceResponse>? = null,
): BaseResponse()

data class SourceResponse(
    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("url"         ) var url         : String? = null,
    @SerializedName("category"    ) var category    : String? = null,
    @SerializedName("language"    ) var language    : String? = null,
    @SerializedName("country"     ) var country     : String? = null
)