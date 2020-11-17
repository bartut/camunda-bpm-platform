<#-- Generated From File: camunda-docs-manual/public/reference/rest/group/get-query-count/index.html -->
{
  <@lib.endpointInfo
      id = "getGroupCount"
      tag = "Group"
      summary = "Get Group Count"
      desc = "Queries for groups using a list of parameters and retrieves the count."
  />

  "parameters" : [

    <#assign requestMethod="GET"/>
    <#include "/lib/commons/group-query-params.ftl" >

    <@lib.parameters
        object = params
        last = true
    />
  ],

  "responses" : {

    <@lib.response
        code = "200"
        dto = "CountResultDto"
        desc = "Request successful."
        examples = ['"example-1": {
                          "summary": "Status 200.",
                          "description": "GET `/group/count?name=Sales`",
                          "value": {
                            "count": 1
                          }
                        }']
    />

    <@lib.response
        code = "400"
        dto = "ExceptionDto"
        desc = "Returned if some of the query parameters are invalid, for example if a `sortOrder`
        parameter is supplied, but no `sortBy` is specified. See the
        [Introduction](${docsUrl}/reference/rest/overview/#error-handling) for the error response
        format."
    />

  }

}