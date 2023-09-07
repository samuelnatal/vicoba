const { pool } = require("../database");

function getLoanInfo(req, res) {
  var { loanID } = req.body;

  //Query to retrieve details of the requested loan by kikoba member
  pool.query(
    'SELECT "loanID","userID" AS "borrowerUserID", "borrowerID" AS "borrowerMemberID", "firstName" AS "borrowerFirstName", "lastName" AS "borrowerLastName", "loanAmount", "loanDesc", guarantee, TO_CHAR("requestedAt",\'YYYY-MM-DD\') AS "requestedAt", loan.status, "statusMessage", (SELECT "firstName" FROM "loan" JOIN "member" ON "guarantor1ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor1FirstName",(SELECT "lastName" FROM "loan" JOIN "member" ON "guarantor1ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor1LastName",(SELECT "firstName" FROM "loan" JOIN "member" ON "guarantor2ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor2FirstName",(SELECT "lastName" FROM "loan" JOIN "member" ON "guarantor2ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor2LastName",(SELECT "firstName" FROM "loan" JOIN "member" ON "guarantor3ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor3FirstName",(SELECT "lastName" FROM "loan" JOIN "member" ON "guarantor3ID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ') AS "guarantor3LastName" FROM "loan" JOIN member ON "borrowerID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "loanID" = ' +
      loanID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows[0]);
    }
  );
}

module.exports = { getLoanInfo };
