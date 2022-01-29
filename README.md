# Python-RSA
RSA implementation in Python with PyCrypto which allows for PKCS#1 padding.


# Rivest-Shamir-Adleman
This is an asymmetric algorithm, meaning there are two different keys; a public key and a private key. 

A user with the public key is able to encrypt a message but only the use rin possession of the private key is able to decrypt it. In public-key (asymmetric) cryptography, the encryption key is public and distinct from the decryption key which is private. A user creates a publishes a public key based on two prime numbers, and an auxiliary value. The prime numbers are kept secret. A message can be encrypted by anyone using the private key but can only be decrypted by someone who knows the prime numbers. Its quite slow so it is not typically used for encryption of user data, but is used to transmit shared keys for a symmetric-key cryptosystem, which in turn is used for bulk encryption/decryption.

RSA relies on the difficulty of factoring the product of two large prime numbers. Provided a large enough key is used, the system is as yet unbreakable.

## Operation
The basic principle of RSA is we find three very large positive integers ``e``,``d``,``n`` such that with modular expinentiation for all integers ``m`` (where 0 ≤ m ≤ n):
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
4: Choose an integer ``e`` s.t. ``1 < e < λ(n)`` and ``gcd(e, λ(n)) = 1``. That is, ``e`` and ``λ(n)`` are coprime.
### Step 2: Key Distribution
### Step 3: Encryption
### Step 4: Decryption

### Quick Note 
Github with PyCharm is a little finnicky. Its best to make the repo first, and then clone it or "Get from VCS" as PyCharm calls it, and then to create your python files inside PyCharm before commit and push.
