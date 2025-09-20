import { useState, useEffect } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Pagination, PaginationContent, PaginationItem, PaginationLink, PaginationNext, PaginationPrevious } from '@/components/ui/pagination';
import { 
  Search, 
  Plus, 
  Download, 
  User,
  Baby,
  Users,
  Calendar,
  Phone,
  Mail,
  MoreHorizontal,
  Eye,
  Edit,
  Trash2,
  AlertTriangle
} from 'lucide-react';
import PageContainer from "@/components/shared/page-container";
import { useNavigate } from 'react-router-dom';
import { ModalProvider, useModal } from '@/components/shared/modal-provider';
import { CreatePatientModal, DetailPatientModal } from '@/components/modals';

// Types pour les patients
interface Patient {
  id: string;
  nom: string;
  prenom: string;
  dateNaissance: string;
  age: number;
  sexe: 'M' | 'F';
  telephone: string;
  email: string;
  adresse: string;
  numeroCarnet: string;
  typePatient: 'adulte' | 'enfant';
  parentId?: string;
  statut: 'actif' | 'inactif' | 'suspendu';
  vaccinationsEnRetard: number;
  derniereVisite: string;
  notes?: string;
}

// Données de démonstration
const patientsData: Patient[] = [
  {
    id: '1',
    nom: 'Dupont',
    prenom: 'Marie',
    dateNaissance: '1985-03-15',
    age: 40,
    sexe: 'F',
    telephone: '01 23 45 67 89',
    email: 'marie.dupont@email.com',
    adresse: '123 Rue de la Santé, 75013 Paris',
    numeroCarnet: 'VAC001234',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-01-15'
  },
  {
    id: '2',
    nom: 'Martin',
    prenom: 'Pierre',
    dateNaissance: '1990-07-22',
    age: 35,
    sexe: 'M',
    telephone: '01 98 76 54 32',
    email: 'pierre.martin@email.com',
    adresse: '456 Avenue de la République, 69001 Lyon',
    numeroCarnet: 'VAC005678',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 1,
    derniereVisite: '2024-01-10'
  },
  {
    id: '3',
    nom: 'Dupont',
    prenom: 'Lucas',
    dateNaissance: '2020-03-20',
    age: 5,
    sexe: 'M',
    telephone: '01 23 45 67 89',
    email: '',
    adresse: '123 Rue de la Santé, 75013 Paris',
    numeroCarnet: 'VAC001235',
    typePatient: 'enfant',
    parentId: '1',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-01-20'
  },
  {
    id: '4',
    nom: 'Martin',
    prenom: 'Emma',
    dateNaissance: '2018-11-15',
    age: 7,
    sexe: 'F',
    telephone: '01 98 76 54 32',
    email: '',
    adresse: '456 Avenue de la République, 69001 Lyon',
    numeroCarnet: 'VAC005679',
    typePatient: 'enfant',
    parentId: '2',
    statut: 'actif',
    vaccinationsEnRetard: 1,
    derniereVisite: '2024-02-15'
  },
  {
    id: '5',
    nom: 'Bernard',
    prenom: 'Sophie',
    dateNaissance: '1978-11-30',
    age: 46,
    sexe: 'F',
    telephone: '01 11 22 33 44',
    email: 'sophie.bernard@email.com',
    adresse: '789 Boulevard du Médecin, 13001 Marseille',
    numeroCarnet: 'VAC009012',
    typePatient: 'adulte',
    statut: 'inactif',
    vaccinationsEnRetard: 2,
    derniereVisite: '2023-12-20'
  },
  {
    id: '6',
    nom: 'Bernard',
    prenom: 'Tom',
    dateNaissance: '2019-06-10',
    age: 6,
    sexe: 'M',
    telephone: '01 11 22 33 44',
    email: '',
    adresse: '789 Boulevard du Médecin, 13001 Marseille',
    numeroCarnet: 'VAC009013',
    typePatient: 'enfant',
    parentId: '5',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-01-10'
  },
  {
    id: '7',
    nom: 'Rousseau',
    prenom: 'Emma',
    dateNaissance: '1992-03-15',
    age: 32,
    sexe: 'F',
    telephone: '01 55 66 77 88',
    email: 'emma.rousseau@email.com',
    adresse: '321 Rue de la Paix, 31000 Toulouse',
    numeroCarnet: 'VAC009014',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 1,
    derniereVisite: '2024-01-20'
  },
  {
    id: '8',
    nom: 'Garcia',
    prenom: 'Carlos',
    dateNaissance: '1975-11-08',
    age: 49,
    sexe: 'M',
    telephone: '01 77 88 99 00',
    email: 'carlos.garcia@email.com',
    adresse: '654 Avenue des Fleurs, 06000 Nice',
    numeroCarnet: 'VAC009015',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-01-25'
  },
  {
    id: '9',
    nom: 'Leroy',
    prenom: 'Jade',
    dateNaissance: '2018-09-22',
    age: 6,
    sexe: 'F',
    telephone: '01 88 99 00 11',
    email: '',
    adresse: '987 Place de la Mairie, 67000 Strasbourg',
    numeroCarnet: 'VAC009016',
    typePatient: 'enfant',
    parentId: '8',
    statut: 'actif',
    vaccinationsEnRetard: 2,
    derniereVisite: '2024-02-01'
  },
  {
    id: '10',
    nom: 'Simon',
    prenom: 'Louis',
    dateNaissance: '1988-12-03',
    age: 36,
    sexe: 'M',
    telephone: '01 99 00 11 22',
    email: 'louis.simon@email.com',
    adresse: '159 Boulevard de la Liberté, 44000 Nantes',
    numeroCarnet: 'VAC009017',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-02-05'
  },
  {
    id: '11',
    nom: 'Blanc',
    prenom: 'Manon',
    dateNaissance: '1995-05-18',
    age: 29,
    sexe: 'F',
    telephone: '01 00 11 22 33',
    email: 'manon.blanc@email.com',
    adresse: '753 Rue du Commerce, 38000 Grenoble',
    numeroCarnet: 'VAC009018',
    typePatient: 'adulte',
    statut: 'actif',
    vaccinationsEnRetard: 1,
    derniereVisite: '2024-02-10'
  },
  {
    id: '12',
    nom: 'Moreau',
    prenom: 'Gabriel',
    dateNaissance: '2020-01-12',
    age: 4,
    sexe: 'M',
    telephone: '01 11 22 33 44',
    email: '',
    adresse: '852 Avenue de la Gare, 34000 Montpellier',
    numeroCarnet: 'VAC009019',
    typePatient: 'enfant',
    parentId: '11',
    statut: 'actif',
    vaccinationsEnRetard: 0,
    derniereVisite: '2024-02-15'
  },
  {
    id: '13',
    nom: 'Vincent',
    prenom: 'Alice',
    dateNaissance: '1982-08-25',
    age: 42,
    sexe: 'F',
    telephone: '01 22 33 44 55',
    email: 'alice.vincent@email.com',
    adresse: '951 Place du Marché, 35000 Rennes',
    numeroCarnet: 'VAC009020',
    typePatient: 'adulte',
    statut: 'inactif',
    vaccinationsEnRetard: 3,
    derniereVisite: '2023-12-20'
  }
];

function PatientContent() {
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('tous');
  const [typeFilter, setTypeFilter] = useState('tous');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 8;

  // Remettre à la page 1 quand les filtres changent
  useEffect(() => {
    setCurrentPage(1);
  }, [searchTerm, statusFilter, typeFilter]);

  const navigate = useNavigate();
  const { openModal } = useModal();

  // Fonctions utilitaires
  const getParentInfo = (parentId?: string) => {
    return patientsData.find(p => p.id === parentId);
  };

  const getTypeIcon = (typePatient: 'adulte' | 'enfant') => {
    return typePatient === 'enfant' ? <Baby className="w-4 h-4" /> : <User className="w-4 h-4" />;
  };

  const getTypeBadge = (typePatient: 'adulte' | 'enfant') => {
    return typePatient === 'enfant' 
      ? <Badge className="bg-blue-500 text-white">Enfant</Badge>
      : <Badge className="bg-green-500 text-white">Adulte</Badge>;
  };

  const getStatutBadge = (statut: Patient['statut']) => {
    switch (statut) {
      case 'actif':
        return <Badge className="bg-green-500 text-white">Actif</Badge>;
      case 'inactif':
        return <Badge variant="outline" className="text-gray-600 border-gray-600">Inactif</Badge>;
      case 'suspendu':
        return <Badge className="bg-red-500 text-white">Suspendu</Badge>;
      default:
        return <Badge variant="outline">Inconnu</Badge>;
    }
  };

  // Filtrage et pagination
  const filteredPatients = patientsData.filter(patient => {
    const matchesSearch = patient.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         patient.prenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         patient.numeroCarnet.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = statusFilter === 'tous' || patient.statut === statusFilter;
    const matchesType = typeFilter === 'tous' || patient.typePatient === typeFilter;
    return matchesSearch && matchesStatus && matchesType;
  });

  const totalPages = Math.ceil(filteredPatients.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedPatients = filteredPatients.slice(startIndex, startIndex + itemsPerPage);

  const handlePatientDetail = (patient: Patient) => {
    navigate(`/medecin/patient-details/${patient.id}`);
  };

  return (
    <PageContainer title="Gestion des Patients" subtitle="Gérez les patients adultes et enfants avec leurs relations parent-enfant">
      <div className="space-y-6">
        {/* En-tête */}
        <div className="flex justify-end items-center">
          <Button onClick={() => openModal('create-patient')} className="flex items-center space-x-2">
            <Plus className="w-4 h-4" />
            <span>Ajouter un patient</span>
          </Button>
        </div>

        {/* Statistiques */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Total Patients</CardTitle>
              <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{patientsData.length}</div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Adultes</CardTitle>
              <User className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">
                {patientsData.filter(p => p.typePatient === 'adulte').length}
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Enfants</CardTitle>
              <Baby className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">
                {patientsData.filter(p => p.typePatient === 'enfant').length}
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Vaccinations en retard</CardTitle>
              <AlertTriangle className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-red-600">
                {patientsData.reduce((acc, p) => acc + p.vaccinationsEnRetard, 0)}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Filtres et recherche */}
        <Card>
          <CardHeader>
            <CardTitle>Recherche et Filtres</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex flex-col md:flex-row gap-4">
              <div className="flex-1">
                <div className="relative">
                  <Search className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    placeholder="Rechercher par nom, prénom ou numéro de carnet..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="pl-10"
                  />
                </div>
              </div>
              <Select value={statusFilter} onValueChange={setStatusFilter}>
                <SelectTrigger className="w-40">
                  <SelectValue placeholder="Statut" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="tous">Tous les statuts</SelectItem>
                  <SelectItem value="actif">Actif</SelectItem>
                  <SelectItem value="inactif">Inactif</SelectItem>
                  <SelectItem value="suspendu">Suspendu</SelectItem>
                </SelectContent>
              </Select>
              <Select value={typeFilter} onValueChange={setTypeFilter}>
                <SelectTrigger className="w-40">
                  <SelectValue placeholder="Type" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="tous">Tous les types</SelectItem>
                  <SelectItem value="adulte">Adultes</SelectItem>
                  <SelectItem value="enfant">Enfants</SelectItem>
                </SelectContent>
              </Select>
              <Button variant="outline" size="icon">
                <Download className="h-4 w-4" />
              </Button>
            </div>
          </CardContent>
        </Card>

        {/* Tableau des patients */}
        <Card>
          <CardHeader>
            <CardTitle>Liste des patients ({filteredPatients.length})</CardTitle>
            <CardDescription>
              Gérez vos patients avec les relations parent-enfant
            </CardDescription>
          </CardHeader>
          <CardContent className="p-0">
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow className="border-b border-border/40 bg-muted/30">
                    <TableHead className="h-12 px-6 text-left align-middle font-semibold">Patient</TableHead>
                    <TableHead className="h-12 px-4 text-left align-middle font-semibold">Type</TableHead>
                    <TableHead className="h-12 px-4 text-left align-middle font-semibold">Contact</TableHead>
                    <TableHead className="h-12 px-4 text-left align-middle font-semibold">Carnet</TableHead>
                    <TableHead className="h-12 px-4 text-left align-middle font-semibold">Statut</TableHead>
                    <TableHead className="h-12 px-4 text-center align-middle font-semibold">Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {paginatedPatients.map((patient, index) => {
                    const parent = patient.parentId ? getParentInfo(patient.parentId) : null;
                    return (
                      <TableRow 
                        key={patient.id}
                        className={`
                          border-b border-border/20 transition-all duration-200 hover:bg-muted/30
                          ${index % 2 === 0 ? 'bg-background' : 'bg-muted/10'}
                        `}
                      >
                        <TableCell className="px-6 py-4">
                          <div className="flex items-center space-x-4">
                            <Avatar className="h-10 w-10 border-2 border-primary/10">
                              <AvatarFallback className="bg-gradient-to-br from-primary/20 to-primary/10 text-primary font-semibold text-sm">
                                {patient.prenom.charAt(0)}{patient.nom.charAt(0)}
                              </AvatarFallback>
                            </Avatar>
                            <div>
                              <div className="font-semibold text-foreground flex items-center space-x-2">
                                {getTypeIcon(patient.typePatient)}
                                <span>{patient.prenom} {patient.nom}</span>
                              </div>
                              <div className="text-sm text-muted-foreground">
                                {patient.age} ans • {patient.sexe === 'M' ? 'Masculin' : 'Féminin'}
                              </div>
                              {patient.typePatient === 'enfant' && parent && (
                                <div className="text-xs text-blue-600 flex items-center space-x-1 mt-1">
                                  <Users className="w-3 h-3" />
                                  <span>Parent: {parent.prenom} {parent.nom}</span>
                                </div>
                              )}
                            </div>
                          </div>
                        </TableCell>

                        <TableCell className="px-4 py-4">
                          {getTypeBadge(patient.typePatient)}
                        </TableCell>

                        <TableCell className="px-4 py-4">
                          <div className="space-y-1">
                            <div className="flex items-center space-x-2 text-sm">
                              <Phone className="h-3 w-3 text-muted-foreground" />
                              <span>{patient.telephone}</span>
                            </div>
                            {patient.email && (
                              <div className="flex items-center space-x-2 text-sm">
                                <Mail className="h-3 w-3 text-muted-foreground" />
                                <span>{patient.email}</span>
                              </div>
                            )}
                          </div>
                        </TableCell>

                        <TableCell className="px-4 py-4">
                          <div className="space-y-1">
                            <div className="font-mono text-sm font-medium">{patient.numeroCarnet}</div>
                            <div className="text-xs text-muted-foreground">
                              Dernière visite: {new Date(patient.derniereVisite).toLocaleDateString('fr-FR')}
                            </div>
                          </div>
                        </TableCell>

                        <TableCell className="px-4 py-4">
                          <div className="space-y-2">
                            {getStatutBadge(patient.statut)}
                            {patient.vaccinationsEnRetard > 0 && (
                              <div className="flex items-center space-x-1 text-xs text-red-600">
                                <AlertTriangle className="h-3 w-3" />
                                <span>{patient.vaccinationsEnRetard} en retard</span>
                              </div>
                            )}
                          </div>
                        </TableCell>

                        <TableCell className="px-4 py-4">
                          <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                              <Button variant="ghost" className="h-8 w-8 p-0">
                                <MoreHorizontal className="h-4 w-4" />
                              </Button>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent align="end">
                              <DropdownMenuLabel>Actions</DropdownMenuLabel>
                              <DropdownMenuItem onClick={() => handlePatientDetail(patient)}>
                                <Eye className="mr-2 h-4 w-4" />
                                Voir les détails
                              </DropdownMenuItem>
                              <DropdownMenuItem onClick={() => openModal('create-rendez-vous', patient)}>
                                <Calendar className="mr-2 h-4 w-4" />
                                Planifier un rendez-vous
                              </DropdownMenuItem>
                              <DropdownMenuSeparator />
                              <DropdownMenuItem>
                                <Edit className="mr-2 h-4 w-4" />
                                Modifier
                              </DropdownMenuItem>
                              <DropdownMenuItem className="text-red-600">
                                <Trash2 className="mr-2 h-4 w-4" />
                                Supprimer
                              </DropdownMenuItem>
                            </DropdownMenuContent>
                          </DropdownMenu>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </div>
          </CardContent>
        </Card>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="flex justify-center">
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious 
                    href="#" 
                    onClick={(e) => {
                      e.preventDefault();
                      if (currentPage > 1) setCurrentPage(currentPage - 1);
                    }}
                  />
                </PaginationItem>
                {[...Array(totalPages)].map((_, i) => (
                  <PaginationItem key={i}>
                    <PaginationLink 
                      href="#" 
                      isActive={currentPage === i + 1}
                      onClick={(e) => {
                        e.preventDefault();
                        setCurrentPage(i + 1);
                      }}
                    >
                      {i + 1}
                    </PaginationLink>
                  </PaginationItem>
                ))}
                <PaginationItem>
                  <PaginationNext 
                    href="#" 
                    onClick={(e) => {
                      e.preventDefault();
                      if (currentPage < totalPages) setCurrentPage(currentPage + 1);
                    }}
                  />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        )}
      </div>

      {/* Modals */}
      <CreatePatientModal />
      <DetailPatientModal />
    </PageContainer>
  );
}

// Export par défaut du composant principal
export default function Patient() {
  return (
    <ModalProvider>
      <PatientContent />
    </ModalProvider>
  );
}
