import dayjs from 'dayjs';

export interface ITrajet {
  id?: number;
  date?: dayjs.Dayjs | null;
  heuredepart?: dayjs.Dayjs | null;
  heurearrivee?: dayjs.Dayjs | null;
  lieudepart?: string | null;
  lieuarrivee?: string | null;
  nombreplace?: number | null;
  prixplace?: number | null;
  marqueVehicule?: string | null;
}

export const defaultValue: Readonly<ITrajet> = {};
