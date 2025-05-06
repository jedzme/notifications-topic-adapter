<?xml version="1.0" ?>

<TestCase name="smokeTest" version="5">

<meta>
   <create version="10.0.0" buildNumber="10.0.0.431" author="admin" date="12/15/2017" host="CACDTL04JG635C" />
   <lastEdited version="10.0.0" buildNumber="10.0.0.431" author="admin" date="12/19/2017" host="CACDTL04JG635C" />
</meta>

<id>259E6722E1CE11E7B538709620524153</id>
<Documentation>Put documentation of the Test Case here.</Documentation>
<IsInProject>true</IsInProject>
<sig>ZWQ9NSZ0Y3Y9NSZsaXNhdj0xMC4wLjAgKDEwLjAuMC40MzEpJm5vZGVzPTE0NDgyNDIzMzY=</sig>
<subprocess>false</subprocess>

<initState>
</initState>

<resultState>
</resultState>

<deletedProps>
</deletedProps>

    <Node name="https GET /health" log=""
          type="com.itko.lisa.ws.rest.RESTNode" 
          version="3" 
          uid="36953570E1CE11E7B538709620524153" 
          think="500-1S" 
          useFilters="true" 
          quiet="false" 
          next="end" > 


      <!-- Assertions -->
<CheckResult assertTrue="false" name="Check HTTP Response Code" type="com.itko.lisa.test.CheckResultHTTPResponseCode">
<log>Assertion name: Check HTTP Response Code checks for: false is of type: HTTP Response Code.</log>
<then>fail</then>
<valueToAssertKey></valueToAssertKey>
        <param>200</param>
</CheckResult>

<url>{{protocol}}://kube:changeme@{{kubeHost}}/{{healthPath}}</url>
<content-type></content-type>
<data-type>text</data-type>
<httpMethod>GET</httpMethod>
<onError>abort</onError>
<encode-test-props-in-url>false</encode-test-props-in-url>
    </Node>


    <Node name="abort" log=""
          type="com.itko.lisa.test.AbortStep" 
          version="1" 
          uid="259E6724E1CE11E7B538709620524153" 
          think="0h" 
          useFilters="true" 
          quiet="true" 
          next="" > 

    </Node>


    <Node name="fail" log=""
          type="com.itko.lisa.test.Abend" 
          version="1" 
          uid="259E6726E1CE11E7B538709620524153" 
          think="0h" 
          useFilters="true" 
          quiet="true" 
          next="abort" > 

    </Node>


    <Node name="end" log=""
          type="com.itko.lisa.test.NormalEnd" 
          version="1" 
          uid="259E6728E1CE11E7B538709620524153" 
          think="0h" 
          useFilters="true" 
          quiet="true" 
          next="fail" > 

    </Node>


</TestCase>
