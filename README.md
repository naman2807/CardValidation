# Card Validation
Created an Android "screen" that would allow a user to enter and submit the following data from an American Express, Discover, MasterCard, or Visa credit card:

1. Credit card number
2. Expiration date (MM/YY format)
3. CVV security code
4. First name
5. Last name

If any of the entered data was invalid, the user needed to be notified so they could correct it. In addition to not having issues like empty fields or badly-formed or expired expiration date, credit card data also had to meet these criteria to be considered valid:

1. Credit card numbers needed to be in a valid American Express, Discover, MasterCard, or Visa format 
2. Credit card numbers also needed to pass Luhn validation - see Luhn algorithm
3. The CVV had to meet these criteria: CVV criteria
4. First and last names could only contain characters that were "alphabetical and spaces"

Link to download apk file: [Card Validation](https://drive.google.com/file/d/1rv3q6HG2YtCxTlQIFQKqHyL_NvrqaNDA/view?usp=drivesdk)
