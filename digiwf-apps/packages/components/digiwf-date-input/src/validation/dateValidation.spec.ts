import {validateDate} from "./dateValidation";

describe("validateDate", () => {
  it("should return true if date is valid", () => {
    expect(validateDate("2023-02-11", true)).toBe(true)
    expect(validateDate("2023-02-11", )).toBe(true)
  });
  it("should return message if date format is incorrect", () => {
    expect(validateDate("2023-02-31")).toBe("Ungültiges Datumsformat")
  });
  it("should return message if native element validity state is false", () => {
    expect(validateDate("", false)).toBe("Ungültiges Datumsformat")
  });
  it("should return message if date is not valid", () => {
    expect(validateDate("2023-02-131")).toBe("Ungültiges Datumsformat")
  });
  it("should return message if year has more than 4 digits", () => {
    expect(validateDate("12023-02-13")).toBe( "Datum darf nicht mehr als 4 Jahreszahlen enthalten")
  });
  it("should return true if date is empty", () => {
    expect(validateDate("")).toBe( true)
    expect(validateDate("", true)).toBe( true)
  });
});