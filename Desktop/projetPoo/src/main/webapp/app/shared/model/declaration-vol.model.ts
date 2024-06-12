import dayjs from 'dayjs';

export interface IDeclarationVol {
  id?: number;
  type?: string | null;
  marque?: string | null;
  modele?: string | null;
  numeroserie?: string | null;
  datevol?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IDeclarationVol> = {};
