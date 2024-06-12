import dayjs from 'dayjs';

export interface ILocation {
  id?: number;
  datedebut?: dayjs.Dayjs | null;
  datefin?: dayjs.Dayjs | null;
  prix?: number | null;
}

export const defaultValue: Readonly<ILocation> = {};
