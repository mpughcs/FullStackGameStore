import { useState } from 'react';
import axios from 'axios';
// Function to handle form submission

  
export default function DatabaseForm({table}){
    const [title, setTitle] = useState('');
    const [rating, setRating] = useState('');
    const [description, setDescription] = useState('');
    const [studio, setStudio] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState(0);
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
  
 {
        return (
            
            <div className='my-4 hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg max-w-md '>
            <form className='flex flex-col gap-2 text-black' onSubmit={handleSubmit}>
              <h1 className='text-2xl font-bold text-black'>Add Game</h1>
              <label className='text-black inline'>Title:
              <input required className='px-1 text-black bg-transparent border-b border-black inline' type="text" name="title" onChange={(e)=>setTitle(e.target.value)}/>
              </label>
              
              <label className='text-black'>Rating:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="rating" onChange={(e) => setRating(e.target.value)} />
              </label>
              
              <label className='text-black'>Description:</label>
              <textarea required className='px-1 text-black bg-transparent border border-black resize-none w-4/5' name="description" onChange={(e) => setDescription(e.target.value)}></textarea>
              
              <label className='text-black'>Studio:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="studio" onChange={(e) => setStudio(e.target.value)} />
              </label>

              <label className='text-black'>Price:
              <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="price" onChange={(e) => setPrice(e.target.value)}/>

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