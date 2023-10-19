import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BsSearch, BsDatabaseFillAdd, BsDatabaseFillDash } from 'react-icons/bs';
import { MdDelete } from 'react-icons/md';
import DatabaseForm from './PurchaseForm';

// fetches all consoles from database

// Refactor the ConsoleSearchBy component into a function component


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

// posts a console to the Database
function postConsole(event) {
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
    .post('http://localhost:8080/consoles', formDataObject, {
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



function deleteConsoleById(id) {
  axios
    .delete(`http://localhost:8080/consoles/${id}`)
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

function Consoles() {
  const [consoles, setConsoles] = useState([]);
  const [search, setSearch] = useState('');
  const [showDatabaseWindow, setShowDatabaseWindow] = useState(false);
  const [showFilteredConsoles, setShowFilteredConsoles] = useState(false);
  const [isFiltered, setIsFiltered] = useState(false);
  
  const [deleteMode, setDeleteMode] = useState(false);
  const [model, setModel] = useState('');
  const [manufacturer, setManufacturer] = useState('');
  const [memoryAmount, setMemoryAmount] = useState('');
  const [processor, setProcessor] = useState('');

  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState(0);

  // Other state variables here

  function filterConsoles(event) {
    isFiltered ? setIsFiltered(false) : setIsFiltered(true);
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const formDataObject = {};
    formData.forEach((value, key) => {
      formDataObject[key] = value;
    });
    console.log(`http://localhost:8080/consoles/manufacturer/${search}`);
    axios
      .get(`http://localhost:8080/consoles/manufacturer/${search}`, formDataObject, {
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
      })
      .then((response) => {
        console.log(response.data);
        setConsoles(response.data); // Update the consoles state with filtered results
        setShowFilteredConsoles(true); // Set the state to render filtered consoles
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }

  async function fetchConsoleData() {
    try {
      const response = await axios.get('http://localhost:8080/consoles');
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }

  // Function to fetch the full list of consoles
  async function fetchAllConsoles() {
    try {
      const response = await axios.get('http://localhost:8080/consoles');
      setConsoles(response.data); // Update the consoles state with the full list
    } catch (error) {
      console.error('Error fetching data:', error);
      setConsoles([]); // Set consoles to an empty array in case of an error
    }
  }

  useEffect(() => {
    async function fetchData() {
      if (!isFiltered) {
        // Fetch all consoles only when isFiltered is false
        await fetchAllConsoles();
      } else {
        // Fetch filtered consoles when isFiltered is true
        const data = await fetchConsoleData();
        setConsoles(data);
      }
    }
    fetchData();
  }, [isFiltered]); // Add isFiltered to the dependency array


  return (
    <div>
      <h2 className='inline flex-nowrap text-lg xl:text-2xl font-bold text-white drop-shadow-md duration-100'> Consoles </h2>

      <BsDatabaseFillAdd className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)} />
      <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setDeleteMode(!deleteMode)} />

        <form className='flex gap-2 text-white' onSubmit={filterConsoles}>
          <div className='flex gap-2 pt-3 flex-nowrap'>
            <h1 className='text-white inline-block min-w-max'>Find By Manufacturer</h1>
            
            <input required className='px-1 h-min text-black' type="text" name="search" onChange={(e) => setSearch(e.target.value)} />
            {isFiltered ? <button className='text-red-400' onClick={fetchConsoleData}> X </button> : <button className='px-1' type="submit"><BsSearch /></button>}
          </div>
        </form>
      <div>
        {/* insertion card */}
        <ul className='flex-col '>
          {showDatabaseWindow &&
            <div className='my-4 hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg max-w-md '>
              <form className='flex flex-col gap-2 text-black' onSubmit={postConsole}>
                <h1 className='text-2xl font-bold text-black'>Add Console</h1>
                <label className='text-black inline'>Model:
                  <input required className='px-1 text-black bg-transparent border-b border-black inline' type="text" name="model" onChange={(e) => setModel(e.target.value)} />
                </label>

                <label className='text-black'>Manufacturer:
                  <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="manufacturer" onChange={(e) => setManufacturer(e.target.value)} />
                </label>

                <label className='text-black'>Memory Amount:
                  <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="memoryAmount" onChange={(e) => setMemoryAmount(e.target.value)} />
                </label>
                <label className='text-black'>Processor:
                  <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="processor" onChange={(e) => setProcessor(e.target.value)} />
                </label>

                <label className='text-black'>Price:
                  <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="price" onChange={(e) => setPrice(e.target.value)} />

                </label>

                <label className='text-black'>Quantity:
                  <input required className='px-1 text-black bg-transparent border-b border-black' type="number" name="quantity" onChange={(e) => setQuantity(Number(e.target.value))} />
                </label>
                <button className='rounded-lg bg-slate-800 text-white p-2 hover:translate-x-1 duration-100' type="submit">Submit</button>
              </form>
            </div>
          }

          {/*consoles from database  */}
            <div>
              <ul className='flex-col'>
                {consoles.map((console) => (
                  <div className="my-4 flex-col hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg drop-shadow-xl" key={console.id}>
                    <div className='flex justify-between'>
                    <h1><span className='text-black text-lg'>Model:</span> {console.model}</h1>

                      {deleteMode && <button onClick={() => (deleteConsoleById(console.id))}><MdDelete className='text-red-600 text-xl hover:scale-[1.1] hover:text-red-300 duration-150' /></button>}
                    </div>

                    <h1><span className='text-black text-lg'>Manufacturer:</span> {console.manufacturer}</h1>
                    <h1><span className='text-black text-lg'>Memory:</span> {console.memoryAmount}</h1>
                    <h1><span className='text-black text-lg'>Processor:</span> {console.processor}</h1>
                    <div >
                      <ul className='flex '>
                        <li className='relative top-[.5rem] '><span className='text-green-500 text-lg '>Price:</span> {console.price}</li>
                        <li className={`relative ml-auto left-2 top-[.5rem] w-min text-right rounded-s-lg p-1 ${changeColor(console.quantity)}`}>QT:{console.quantity}</li>
                      </ul>
                    </div>


                  </div>
                ))}
              </ul>

            </div>
        </ul>

      </div>
    </div>
  );
}




export default Consoles;
