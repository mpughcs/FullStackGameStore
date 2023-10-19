import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BsSearch,BsDatabaseFillAdd,BsDatabaseFillDash  } from 'react-icons/bs';
import {MdDelete} from 'react-icons/md';



// fetches all shirts from database
async function fetchShirtData() {
  try {
    const response = await axios.get('http://localhost:8080/tshirts');
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}

// Refactor the ShirtSearchBy component into a function component


function changeColor(quantity) {
    if (quantity < 5) {
        console.log('Changing color to text-red-500');
        return 'text-red-500';
      } else if (quantity < 10) {
        console.log('Changing color to text-yellow-500');
        return 'text-yellow-500';
      } else if (quantity < 20) {
        console.log('Changing color to text-green-500');
        return 'text-green-500';
      } else if (quantity < 50) {
        console.log('Changing color to text-blue-500');
        return 'text-blue-500';
      }
    }

  // posts a Shirt to the Database
function postShirt(event) {
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
      .post('http://localhost:8080/tshirts', formDataObject, {
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
      // reloads after posting
      window.location.reload();
     
  }

  function deleteShirtById(id) {
    axios
      .delete(`http://localhost:8080/tshirts/${id}`)
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
      // reloads after posting
      window.location.reload();
  }

function shirts() {
  const [shirts, setShirts] = useState([]);
  const [search, setSearch] = useState('');
  const [searchBy, setSearchBy] = useState('color');
  const [isFiltered, setIsFiltered] = useState(false);
  const [showDatabaseWindow, setShowDatabaseWindow] = useState(false);
  const [showFilteredShirts, setShowFilteredShirts] = useState(false);
  const [size, setSize] = useState('');
  const [color, setColor] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState(0);
  const [deleteMode, setDeleteMode] = useState(false);  
  
  function filterShirts(event) {
    isFiltered? setIsFiltered(false): setIsFiltered(true);

    event.preventDefault();
      const form = event.target;
      const formData = new FormData(form);
      const formDataObject = {};
      formData.forEach((value, key) => {
        formDataObject[key] = value;
      });
      console.log(`http://localhost:8080/tshirts/${searchBy}/${search}`);
      axios
        .get(`http://localhost:8080/tshirts/${searchBy}/${search}`, formDataObject, {
          headers: {
            'Content-Type': 'application/json', // Set the content type to JSON
          },
        })
        .then((response) => {
          console.log(response.data);
          setShirts(response.data); // Update the shirts state with filtered results
          setShowFilteredShirts(true); // Set the state to render filtered shirts
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
        });
    }
  async function fetchShirtData() {
      try {
        const response = await axios.get('http://localhost:8080/tshirts');
        console.log(response.data);
        setShirts(response.data);
        return response.data;
      } catch (error) {
        console.error('Error fetching data:', error);
        return [];
      }
    }
   

  useEffect(() => {
    async function fetchData() {
      const data = await fetchShirtData();
      setShirts(data);
    }
    fetchData();
  }, []); 

  
  return (
    <div>
      <h2 className='inline flex-nowrap text-lg xl:text-2xl font-bold text-white drop-shadow-md duration-100'> Shirts </h2>

      <BsDatabaseFillAdd className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/>
      <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setDeleteMode(!deleteMode)}/>
     
        <form className='flex gap-2 text-white' onSubmit={filterShirts}>
          <div className='flex gap-2 pt-3 flex-nowrap'>
            <h1 className='text-white inline-block min-w-max'>Find By</h1>
            <select className='px-1 inline-block w-min h-min flex-1 text-black' name="searchBy" onChange={(e) => setSearchBy(e.target.value)}>
              <option value="color">Color</option>
              <option value="size">Size</option>
            </select>
            :
            <input required className='px-1 h-min text-black' type="text" name="search" onChange={(e) => setSearch(e.target.value)} />
            {isFiltered ? <button className='text-red-400' onClick={fetchShirtData}> X </button> : <button className='px-1' type="submit"><BsSearch /></button>}
          </div>
        </form>
    <div>
      {/* insertion card */}
      <ul className='flex-col '>
        {showDatabaseWindow && 
        <div className='my-4 hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg max-w-md '>
        <form className='flex flex-col gap-2 text-black' onSubmit={postShirt}>
          <h1 className='text-2xl font-bold text-black'>Add Shirt</h1>
          
          <label className='text-black'>Size:
          <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="size" onChange={(e) => setSize(e.target.value)} />
          </label>

          <label className='text-black'>Color:
          <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="color" onChange={(e) => setColor(e.target.value)} />
          </label>
          
          <label className='text-black'>Description:</label>
          <textarea required className='px-1 text-black bg-transparent border border-black resize-none w-4/5' name="description" onChange={(e) => setDescription(e.target.value)}></textarea>
          
          

          <label className='text-black'>Price:
          <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="price" onChange={(e) => setPrice(e.target.value)}/>

          </label>

          <label className='text-black'>Quantity:
          <input required className='px-1 text-black bg-transparent border-b border-black' type="number" name="quantity" onChange={(e) => setQuantity(Number(e.target.value))}/>
          </label>
          <button className='rounded-lg bg-slate-800 text-white p-2 hover:translate-x-1 duration-100' type="submit">Submit</button>
        </form>
        </div>   
      }
        
      {/*shirts from database  */}
        {shirts.map((Shirt) => (
          <div className="my-4 flex-col  hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg "key={Shirt.Shirt_id}>
            <div className='flex justify-between'>
            <h1><span className='text-black text-lg'>Color:</span> {Shirt.color}</h1>
            
            {deleteMode && <button onClick={()=>(deleteShirtById(Shirt.id))}><MdDelete className='text-red-600 text-xl hover:scale-[1.1] hover:text-red-300 duration-150'/></button>}
            </div>
            <span><h1 className='text-black text-lg'> Size: </h1> <p className='max-w-xs px-1'>{Shirt.size}</p></span>
            
            <span><h1 className='text-black text-lg'> Description: </h1> <p className='max-w-xs px-1'>{Shirt.description}</p></span>
            <div>
              <ul className='flex'>
              <li className='relative top-[.5rem] '><span className='text-green-500 text-lg '>Price:</span> {Shirt.price}</li>
              <li className={`relative ml-auto left-2 top-[.5rem] w-min text-right rounded-s-lg p-1 ${changeColor(Shirt.quantity)}`}>QT:{Shirt.quantity}</li>
              </ul>
            </div>

          </div>
        ))}
      </ul>

    </div>
    </div>
  );
}




export default shirts;
