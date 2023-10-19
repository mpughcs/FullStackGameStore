import { useState } from 'react';
import axios from 'axios';
// Function to handle form submission

  
export default function PurchaseForm({table}){
    const [title, setTitle] = useState('');
    const [rating, setRating] = useState('');
    const [description, setDescription] = useState('');
    const [studio, setStudio] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState(0);
    const [city, setCity] = useState('');
    const [state, setState] = useState('');
    const [street, setStreet] = useState('');
    const [customerName, setCustomerName] = useState('');
    const [zipcode, setZipcode] = useState('');
    const [itemType, setItemType] = useState('');
    const [itemId, setItemId] = useState('');
    const [purchaseQuantity, setPurchaseQuantity] = useState(0);

    // Function to handle form submission
function handleSubmit(event) {
    event.preventDefault(); // Prevent the default form submission
  
    // Collect form data
    const form = event.target;
    const formData = new FormData(form);
  
    // Create a JavaScript object from the form data
    const formDataObject = {};
    formData.forEach((value, key) => {
      formDataObject[key] = value;
    });
  
    // Send the JSON data using Axios
    axios
      .post('http://localhost:8080/games', formDataObject, {
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
      })
      .then((response) => {
        if (response.status == 200) {
          // Handle a successful response
          console.log('Data sent successfully');
          // You can perform further actions here
        } else {
          // Handle errors
          console.error('Error sending data');
        }
      })
      .catch((error) => {
        console.error('Network error:', error);
      });
  }
  // private String customerName;
// private String street;
// private String city;
// private String state;
// private String zipcode;
// private String itemType;
// private Integer itemId;
// private Integer quantity;
 {
        return (            
            <div className='my-4 hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg max-w-md '>
            <form className='flex flex-col gap-2 text-black' onSubmit={handleSubmit}>
              <h1 className='text-2xl font-bold text-black'>Check out</h1>
              <label className='text-black inline'>Your Name:
              <input required className='px-1 text-black bg-transparent border-b border-black inline' type="text" name="customerName" onChange={(e)=>setCustomerName(e.target.value)}/>
              </label>
              
              <label className='text-black'>Street:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="street" onChange={(e) => setStreet(e.target.value)} />
              </label>
              
              <label className='text-black'>City:</label>
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="City" onChange={(e) => setCity(e.target.value)} />

              
              <label className='text-black' onChange={(e)=>setState(e.target.value)}>State:
              <select>
                  <option value="AL">AL</option>
                  <option value="AK">AK</option>
                  <option value="AR">AR</option>
                  <option value="AZ">AZ</option>
                  <option value="CA">CA</option>
                  <option value="CO">CO</option>
                  <option value="CT">CT</option>
                  <option value="DC">DC</option>
                  <option value="DE">DE</option>
                  <option value="FL">FL</option>
                  <option value="GA">GA</option>
                  <option value="HI">HI</option>
                  <option value="IA">IA</option>
                  <option value="ID">ID</option>
                  <option value="IL">IL</option>
                  <option value="IN">IN</option>
                  <option value="KS">KS</option>
                  <option value="KY">KY</option>
                  <option value="LA">LA</option>
                  <option value="MA">MA</option>
                  <option value="MD">MD</option>
                  <option value="ME">ME</option>
                  <option value="MI">MI</option>
                  <option value="MN">MN</option>
                  <option value="MO">MO</option>
                  <option value="MS">MS</option>
                  <option value="MT">MT</option>
                  <option value="NC">NC</option>
                  <option value="NE">NE</option>
                  <option value="NH">NH</option>
                  <option value="NJ">NJ</option>
                  <option value="NM">NM</option>
                  <option value="NV">NV</option>
                  <option value="NY">NY</option>
                  <option value="ND">ND</option>
                  <option value="OH">OH</option>
                  <option value="OK">OK</option>
                  <option value="OR">OR</option>
                  <option value="PA">PA</option>
                  <option value="RI">RI</option>
                  <option value="SC">SC</option>
                  <option value="SD">SD</option>
                  <option value="TN">TN</option>
                  <option value="TX">TX</option>
                  <option value="UT">UT</option>
                  <option value="VT">VT</option>
                  <option value="VA">VA</option>
                  <option value="WA">WA</option>
                  <option value="WI">WI</option>
                  <option value="WV">WV</option>
                  <option value="WY">WY</option>
                </select>
              
              </label>
              <label className='text-black'>Zipcode:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="zipcode" onChange={(e) => setZipcode(e.target.value)}/>
              </label>
             
              <label className='text-black'>Quantity:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="number" name="quantity" onChange={(e) => setQuantity(Number(e.target.value))}/>
              </label>
              <button className='rounded-lg bg-slate-800 text-white p-2 hover:translate-x-1 duration-100' type="submit">Submit</button>
            </form>
          </div>
        )
    }


}