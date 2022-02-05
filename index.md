# Rivest-Shamir-Adleman Encryption
This is an asymmetric algorithm, meaning there are two different keys; a public key and a private key. 

A user with the public key is able to encrypt a message but only the use rin possession of the private key is able to decrypt it. In public-key (asymmetric) cryptography, the encryption key is public and distinct from the decryption key which is private. A user creates a publishes a public key based on two prime numbers, and an auxiliary value. The prime numbers are kept secret. A message can be encrypted by anyone using the private key but can only be decrypted by someone who knows the prime numbers. Its quite slow so it is not typically used for encryption of user data, but is used to transmit shared keys for a symmetric-key cryptosystem, which in turn is used for bulk encryption/decryption.

RSA relies on the difficulty of factoring the product of two large prime numbers. Provided a large enough key is used, the system is as yet unbreakable.

## Operation
The basic principle of RSA is we find three very large positive integers ``e``,``d``,``n`` such that with modular expinentiation for all integers ``m`` (where ``0 ≤ m ≤ n``):
![image](https://user-images.githubusercontent.com/78870995/151672651-33579def-5cf9-4ff3-a579-2afcdcb0b71e.png)
and that even when knowing ``e``, ``n`` or ``m``, its extremely difficult to find ``d``. 

The public key can be known by everypne and is used for encrypting the messages. The intention is that text enciphered with the public key can only be reasonably decrypted by using the private key.

The public key is represented by integers ``n`` and ``e``; and the private key by ``d`` (``n`` is also used during decryption). ``m`` represents the prepared message.

### Step 1: Key Generation

1: Choose two distinct prime numbers ``p`` and ``q``. These should be chosen at random and be similar in magnitude but differ in length by some digits to make factoring more difficult. These integers are kept secret.
  
2: Compute ``n = pq``. ``n`` is used as the modulus for both keys. Its length in bits is the key length. ``n`` is published as part of the public key.
  
3: Compute Carmichael's totient ``λ(n)`` where ``n = pq``. 
Therefore ``λ(n) = lcm(λ(p), λ(q))``. Since ``p`` and ``q`` are prime, that means ``λ(p) = φ(p) = p − 1``. So ``λ(n) = lcm(p − 1, q − 1)``. 
``λ(n)`` is kept secret. 
  
4: Choose an integer ``e`` s.t. ``1 < e < λ(n)`` and ``gcd(e, λ(n)) = 1``. That is, ``e`` and ``λ(n)`` are coprime. ``e`` having short bit-length and small Hamming weight (the number of symbols that are different from 0) results in more efficient encryption. Common value for ``e`` is ``65,537``.

5: Determine ``d = e^-1 (mod λ(n))``; i.e. ``d`` can be computed easily using extended Euclidean, since ``e`` and ``λ(n)`` being coprime means this equation is a form of Bezout's identity, with ``d`` being a coefficient in it. ``d`` is kept secret as the 'private key exponent'.

The public key consists of the modulus ``n`` and the public (encryption) exponent ``e``. 

The private key consists of the private (decryption) exponent ``d``, which must be kept secret. ``p``, ``q``, and ``λ(n)`` also must be kept secret because you can use them to calculate ``d``. 


The original iteration of RSA carried out key generation by choosing ``d`` and then computing ``e`` as the 'modular multiplicative inverse' of ``d modulo φ(n)`` but modern implementations like with PKCS#1, they do the reverse and choose ``e`` and compute ``d``. 
  
### Step 2: Key Distribution

Using the age-old Bob and Alice; suppose Bob wants to send a message to Alice. With RSA, Bob needs to know Alice's public key in order to encrypt the message, adn Alice uses her private key to decrypt the message.

To allow Bob to send his encrypted messages, Alice transmits her public key ``(n,e)`` to Bob. Alice's private key ``(d)`` is NEVER distributed.

### Step 3: Encryption

After Bob gets Alice's public key, he can send a message ``M`` to Alice. To do so, ``M`` (the plaintext) is turned into an int ``m`` where ``0 ≤ m ≤ n`` by using a previously-agreed-upon padding scheme. Bob then computes the ciphertext ``c``, using Alice's public key ``e``: ![image](https://user-images.githubusercontent.com/78870995/151674096-f97ae162-2bb9-499d-bd67-9b43aba81393.png)

Bob then transmits ``c`` to Alice.

### Step 4: Decryption

Alice can decrypt the ciphertext ``c`` to get the plaintext ``m`` using her private key exponent ``d``: ![image](https://user-images.githubusercontent.com/78870995/151674134-303284bc-9a5f-4ec8-805e-e3977961c928.png)

Given ``m``, Alice can recover the original message ``M`` by reversing the padding scheme.

## Simplified Algorithm (no padding)
    Consider two prime numbers p and q.
    Compute n = p*q
    Compute ϕ(n) = (p – 1) * (q – 1)
    Choose e such gcd(e , ϕ(n) ) = 1
    Calculate d such e*d mod ϕ(n) = 1
    Public Key {e,n} Private Key {d,n}
    Cipher text C = Pe mod n where P = plaintext
    For Decryption D = Dd mod n where D will refund the plaintext.
    
## Padding

## Note

If you attempt to encrypt a file more than once, you'll recieve an error ```javax.crypto.IllegalBlockSizeException```. 

Speaking of; I went through the effort of adding file encryption and halfway through my implementation I was rudely reminded that RSA shouldn't be used for direct encryption of data. I got ```IllegalBlockSizeException: Data must not be longer than 256 bytes``` a number of times before I realised that a better option is to encrypt files with AES. I'm going to save such an implementation for another project as I just wanted to focus on RSA for this, so I will leave the relevant file encryption/decryption code here for my own future use.

### Inside Main - JOptionPane cases
```
 case 2: //encrypt a file
                String filename = JOptionPane.showInputDialog ("Enter name of a file in the project directory");
                File file = new File(filename);
                encryptFile(file, publicKey);
                JOptionPane.showMessageDialog(null, "File has been encrypted successfully", "File Encryption", JOptionPane.INFORMATION_MESSAGE);
                main(args);
                break;
            case 3: //decrypt a file
                String filename2 = JOptionPane.showInputDialog ("Enter name of a file in the project directory");
                File file2 = new File(filename2);
                decryptFile(file2, privateKey);
                JOptionPane.showMessageDialog(null, "File has been decrypted successfully", "File Decryption", JOptionPane.INFORMATION_MESSAGE);
                //main(args);
                break;
```


### Encrypting a File
```
 public static void encryptFile(File file, String publicKey) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));

        byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);

        Files.write(file.toPath(), encryptedFileBytes);

    }
 ```


### Decrypting a File
```
 public static void decryptFile(File file, String privateKey) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IOException {
        byte[] encryptedFileBytes = Files.readAllBytes(file.toPath());
        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
        byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
        Files.write(file.toPath(), decryptedFileBytes);

    }
 ```
