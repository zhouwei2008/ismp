<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
        <title><g:layoutTitle default="Grails" /></title>
        <g:layoutHead />
        <script charset="utf-8" src="${resource(dir:'js',file:'arale.js')}?t=${new Date().getTime()}"></script>
        <script charset="utf-8" src="${resource(dir:'js',file:'common.js')}"></script>
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" media="all" />
        <link charset="utf-8" rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" media="all" />
    </head>
	<body>
        <!--container start-->

            <g:if test="${session.cmCustomerOperator}"><g:render template="/layouts/header"/></g:if>

            <g:layoutBody />

            %{--<script charset="utf-8" src="${resource(dir:'js',file:'roll.js')}"></script>--}%

            <g:render template="/layouts/footer"/>

        <!--container end-->
    </body>
</html>