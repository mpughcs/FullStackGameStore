import React from 'react'
import { useState } from 'react'
import {BsSearch} from 'react-icons/bs'
// pass in a list of fields to search by
export default function ConsoleSearchBy(){

    return (
        <div>
            <form className='flex gap-2  text-white' onSubmit>
                <div className='flex gap-2 pt-3 flex-nowrap'>
                <h1 className='text-white inline-block min-w-max'>Find By manufacturer</h1>
                :
                <input required className='px-1 h-min text-black' type="text" name="search" onChange={(e)=>setSearch(e.target.value)}/>
                <button className='px-1' type="submit"><BsSearch/></button>
                </div>
            </form>
        </div>
    )

}