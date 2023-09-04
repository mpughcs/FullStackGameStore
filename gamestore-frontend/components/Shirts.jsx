import React, { useEffect, useState } from 'react';
import axios from 'axios';

async function fetchGameData() {
  try {
    const response = await axios.get('http://localhost:8080/tshirts');
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}

function Shirts() {
  const [shirts, setShirts] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const data = await fetchGameData();
      setShirts(data);
    }
    fetchData();
  }, []); // Fetch data when the component mounts

  return (
    <div className=''>
      <ul className='flex-col'>
        {console.log(shirts)}
        {shirts.map((shirt) => (
          <div className="my-4 flex-col hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg "key={shirt.id}>
            <h1><span className='text-black text-lg'>Size:</span> {shirt.size}</h1>
            <h1><span className='text-black text-lg'>Color:</span> {shirt.color}</h1>
            <span><h1 className='text-black text-lg'> Description</h1> <p className='max-w-xs px-1'>{shirt.description}</p></span>
            
            {/* <div className='relative ml-auto left-2 top-[.5rem] bg-slate-400 w-min text-right rounded-s-lg p-1'>{shirt.quantity}</div> */}
            {/* use change color to set the text color of shirt.quantity */}
            <div >
              <ul className='flex '>
              <li className='relative top-[.5rem] '><span className='text-green-500 text-lg '>Price:</span> {shirt.price}</li>
              <li className={`relative ml-auto left-2 top-[.5rem] w-min text-right rounded-s-lg p-1 ${changeColor(shirt.quantity)}`}>{shirt.quantity}</li>
              </ul>
            </div>


          </div>
        ))}
      </ul>

    </div>
  );
}


// i want a function that will change the color of the quantity div based on the quantity number
// if quantity is less than 5, color is red
// if quantity is less than 10, color is yellow
// if quantity is greater than 20, color is green
// if quantity is greater than 50, color is blue

function changeColor(quantity) {
  if (quantity < 5) {
    return 'text-red-500';
  } else if (quantity < 10) {
    return 'text-yellow-500';
  } else if (quantity < 20) {
    return 'text-green-500';
  } else if (quantity < 50) {
    return 'text-blue-500';
  }
}



export default Shirts;
