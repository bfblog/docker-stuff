import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;

Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "user", "password")

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), c)

/*
String keyfile = "/tmp/key"

def ksm1 = new CertificateCredentialsImpl.FileOnMasterKeyStoreSource(keyfile)
Credentials ck1 = new CertificateCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "password", ksm1)

def ksm2 = new CertificateCredentialsImpl.UploadedKeyStoreSource(keyfile)
Credentials ck2 = new CertificateCredentialsImpl(CredentialsScope.GLOBAL,java.util.UUID.randomUUID().toString(), "description", "password", ksm2)

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), ck1)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), ck2)
*/