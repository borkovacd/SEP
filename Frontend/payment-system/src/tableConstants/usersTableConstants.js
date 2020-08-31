import React from "react";
import { Button, ButtonGroup } from "reactstrap";
import { Link } from "react-router-dom";

/* 
    Now we will create our constant file which will be responsible to handle all the columns to be presented inside the table and 
    we can have a glimpse of what we have to handle in our table component to render the data accordingly and our component should be generic too
 */

// This is the table constant/settings which needed to render table elements
export const usersTableConstants = (hadleBlock, handleDelete) => {
  return [
    {
      title: "First Name",
      render: rowData => {
        return <span>{rowData.firstName}</span>;
      }
    },
    {
      title: "Last Name",
      render: rowData => {
        return <span>{rowData.lastName}</span>;
      }
    },
    {
      title: "Username",
      render: rowData => {
        return <span>{rowData.username}</span>;
      }
    },
    {
      title: "Email",
      render: rowData => {
        return <span>{rowData.email}</span>;
      }
    },
    {
      title: "Blocked",
      render: rowData => {
        return <span>{rowData.blocked + ""}</span>;
      }
    },

    {
      title: "Action",
      render: rowData => {
        return (
          <ButtonGroup>
            <Button
              className="btn btn-success"
              tag={Link}
              to={"/edit-user/" + rowData.id}
            >
              Edit
            </Button>
            <Button
              className="btn btn-warning ml-1"
              onClick={hadleBlock(rowData)}
            >
              Block
            </Button>
            <Button
              className="btn btn-danger ml-1"
              onClick={handleDelete(rowData)}
            >
              Delete
            </Button>
          </ButtonGroup>
        );
      }
    }
  ];
};
